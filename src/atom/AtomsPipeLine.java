package atom;

import engine.GameContainer;
import engine3d.Camera;
import engine3d.Vec4df;
import engine3d.matrix.Mat4x4;
import engine3d.matrix.MatrixMath;
import molecule.Bone;
import molecule.MatrixMathAtom;
import molecule.Molecule;

import java.util.ArrayList;

/**
 * This class encapsulates all the methods
 * to render 3D Atoms
 *
 * @author Sergio Martí Torregrosa
 * @date 08/09/2020
 */
public class AtomsPipeLine {

    /**
     * We need this for the offsetScaleTriangle method.
     * How is a susceptible point to increase the computational
     * cost, we define this constant here
     */
    private final Vec4df OFFSET_VIEW = new Vec4df(1.0f, 1.0f, 0.0f, 0.0f);

    /**
     * We need this for the triangleClipAgainstPlane
     */
    private final Vec4df PLANE_POINT = new Vec4df(0.0f, 0.0f, 0.1f);

    /**
     * Same as the planePoint
     */
    private final Vec4df PLANE_NORMAL = new Vec4df(0.0f, 0.0f, 1.0f);

    /**
     * The camera object
     */
    private Camera cameraObj;

    /**
     * The view matrix
     */
    private Mat4x4 matView;

    /**
     * The projection matrix
     */
    private Mat4x4 matProjection;

    /**
     * The world matrix transformation
     */
    private Mat4x4 worldMatrix;

    /**
     * The specific renderer for atoms
     */
    private AtomsRenderer r;

    /**
     * The screen width
     */
    private int screenWidth;

    /**
     * The screen height
     */
    private int screenHeight;

    /**
     * The constructor
     *
     * @param gc the GameContainerObject
     */
    public AtomsPipeLine(GameContainer gc) {
        screenWidth = gc.getWidth();
        screenHeight = gc.getHeight();

        r = new AtomsRenderer(gc);

        cameraObj = new Camera();
        cameraObj.setOrigin(new Vec4df(0.0f, 0.0f, -10.0f));
        matView = cameraObj.getMatView();

        matProjection = buildNormalProjectionMatrix(gc.getWidth(), gc.getHeight());

        worldMatrix = MatrixMath.matrixMakeIdentity();
    }

    /**
     * This method builds the normal projection matrix
     *
     * @param width the screen width
     * @param height the screen height
     * @return the normal projection matrix
     */
    private Mat4x4 buildNormalProjectionMatrix(int width, int height) {
        float near = 0.1f;
        float far = 1000.0f;
        float fovDegrees = 90.0f;
        float aspectRatio = (float)height / (float)width;
        return MatrixMath.matrixMakeProjection(fovDegrees, aspectRatio, near, far);
    }

    /**
     * This method transform the molecule by the worldMatrix
     * of the AtomsPipeLine
     *
     * In this approach the radius bone of the atom is need to
     * treat in different way of the others. The origin
     * of this bone needs to be transformed (the point A),
     * but the point B has to be fixed always to the side
     * of the point A
     *
     * For this reason, all bones are transformed, and after,
     * the point B of the bone with index 0 (radius bone)
     * is recalculated
     *
     * @param molecule the molecule to transform
     */
    private ArrayList<Atom> transformMolecule(Molecule molecule) {
        ArrayList<Atom> atomsTransformed = new ArrayList<>();
        Atom atomTransformed;

        for ( Atom atom : molecule.getAtoms() ) {
            atomTransformed = new Atom(atom);

            Bone[] bones = new Bone[atom.getBones().length];

            for ( int i = 0; i < atom.getBones().length; i++ ) {
                bones[i] = MatrixMathAtom.matrixMultiplyBone(worldMatrix, atom.getBones()[i]);
            }

            Vec4df pointRadius = new Vec4df(bones[0].getPointA());
            pointRadius.addToX(atom.getRadius());
            bones[0].setPointB(pointRadius);

            atomTransformed.setBones(bones);

            atomsTransformed.add(atomTransformed);
        }

        return atomsTransformed;
    }

    /**
     * This method offsets and scales the atom's bones to get something
     * visible on the screen
     *
     * @param atom the atom to offset
     * @param width the screen width
     * @param height the screen height
     */
    private void offSetProjectedAtom(Atom atom, int width, int height) {
        for ( Bone bone : atom.getBones() ) {
            bone.setPointA(MatrixMathAtom.vectorDiv(bone.getPointA(), bone.getPointA().getW()));
            bone.setPointB(MatrixMathAtom.vectorDiv(bone.getPointB(), bone.getPointB().getW()));

            bone.getPointA().multiplyXBy(-1.0f);
            bone.getPointA().multiplyYBy(-1.0f);

            bone.getPointB().multiplyXBy(-1.0f);
            bone.getPointB().multiplyYBy(-1.0f);

            bone.setPointA(MatrixMathAtom.vectorAdd(bone.getPointA(), OFFSET_VIEW));

            bone.setPointB(MatrixMathAtom.vectorAdd(bone.getPointB(), OFFSET_VIEW));

            bone.getPointA().multiplyXBy(0.5f * width);
            bone.getPointA().multiplyYBy(0.5f * height);

            bone.getPointB().multiplyXBy(0.5f * width);
            bone.getPointB().multiplyYBy(0.5f * height);
        }
    }

    /**
     * This method projects the atoms
     *
     * @param atoms the atoms to project
     * @param width the screen width
     * @param height the screen height
     * @return the projected atoms
     */
    private ArrayList<Atom> projectAtoms(ArrayList<Atom> atoms, int width, int height) {
        ArrayList<Atom> atomsProjected = new ArrayList<>();
        Atom atomViewed, atomProjected;

        for ( Atom atom : atoms ) {

            atomViewed = new Atom(
                    atom.getId(),
                    atom.getRadius(),
                    MatrixMathAtom.matrixMultiplyBones(matView, atom.getBones()),
                    atom.getImage(),
                    atom.getAtomKind(),
                    atom.getColor()
            );

            atomProjected = new Atom(
                    atomViewed.getId(),
                    atomViewed.getRadius(),
                    MatrixMathAtom.matrixMultiplyBones(matProjection, atomViewed.getBones()),
                    atom.getImage(),
                    atom.getAtomKind(),
                    atom.getColor()
            );

            offSetProjectedAtom(atomProjected, width, height);

            atomsProjected.add(atomProjected);
        }

        return atomsProjected;
    }

    /**
     * This method renders in screen the molecule
     * passed by parameter
     *
     * @param molecule the
     */
    public void renderMolecule(Molecule molecule) {
        ArrayList<Atom> atomsTransformed = transformMolecule(molecule);

        ArrayList<Atom> atomsProjected = projectAtoms(atomsTransformed, screenWidth, screenHeight);

        atomsProjected.sort(
                (o1, o2) -> {
                    float medZ1 = 0;
                    for ( int i = 1; i < o1.getBones().length; i++ ) { // todo esto da igual
                        medZ1 += o1.getBones()[i].getPointB().getZ();
                    }
                    medZ1 /= (o1.getBones().length - 1);

                    float medZ2 = 0;
                    for ( int i = 1; i < o2.getBones().length; i++ ) {
                        medZ2 += o2.getBones()[i].getPointB().getZ();
                    }
                    medZ2 /= (o2.getBones().length - 1);
                    return Float.compare(medZ2, medZ1);
                }
        );

        r.renderAtoms(atomsProjected);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Camera getCameraObj() {
        return cameraObj;
    }

    public Mat4x4 getWorldMatrix() {
        return worldMatrix;
    }

    public AtomsRenderer getRenderer() {
        return r;
    }

    public void setCamera(Camera camera) {
        this.cameraObj = camera;
    }

    public void setMatView(Mat4x4 matView) {
        this.matView = matView;
    }

    public void setTransform(Mat4x4 transform) {
        worldMatrix = transform;
    }

    public void setRenderer(AtomsRenderer r) {
        this.r = r;
    }

    /**
     * This method sets the camera origin to the new origin pass as
     * a parameter and reset the matView. This two operations are needed
     * if is wanted to see a visible change in the final rendering.
     * In this way, the inner working of the matView is encapsulate
     * inside the pipeline class. Before, the user had to set
     * the matView when the camera object is modified
     * @param origin the new origin for the camera object
     */
    public void setCameraOrigin(Vec4df origin) {
        cameraObj.setOrigin(origin);
        setMatView(cameraObj.getMatView());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

}
