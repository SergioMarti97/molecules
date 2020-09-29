package atom;

import engine.GameContainer;
import engine.gfx.HexColors;
import engine2d.Renderer2D;
import engine2d.Transform2D;
import engine3d.Vec4df;
import molecule.Bone;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is a specific Renderer for render Atoms.
 * Encapsulates all methods which draw in screen Atoms and related things.
 *
 * @author Sergio Martí Torregrosa
 * @date 08/09/2020
 */
public class AtomsRenderer extends Renderer2D {

    /**
     * The flag for draw the atom images
     */
    private boolean isDrawingAtomImages = true;

    /**
     * The flag for draw the bones
     */
    private boolean isDrawingBones = false;

    /**
     * The flag for draw the radius bones
     */
    private boolean isDrawingRadius = false;

    /**
     * The flag to draw the text bones
     *
     * For each bone is drawn its index of the
     * bone array of the atom. It's useful
     * for identify the bones which is wanted
     * to join a new atom when it's building
     * a molecule
     */
    private boolean isDrawingBonesTexts = false;

    /**
     * The flag to draw the atoms index
     */
    private boolean isDrawingAtomsIndex = false;

    /**
     * The Constructor
     * @param gc the GameContainer Object
     */
    public AtomsRenderer(GameContainer gc) {
        super(gc);
    }

    /**
     * This method only draws the radius bone or stick used to
     * scale to the correct size the image of the atom
     * It's useful for debugging
     * This bone is always the first (the element with index 0)
     * of the bone array of the atoms
     *
     * @param atom the atom whose radius bone is to be drawn
     */
    private void renderRadiusBone(Atom atom) {
        drawLine(
                (int)(atom.getBones()[0].getPointA().getX()),
                (int)(atom.getBones()[0].getPointA().getY()),
                (int)(atom.getBones()[0].getPointB().getX()),
                (int)(atom.getBones()[0].getPointB().getY()),
                atom.getColor()
        );
    }

    /**
     * This method draws the bones of the atom
     *
     * @param atom the atom whose bones is to be drawn
     * @param isDrawingText a boolean flag to draw the orbital number, useful for debug
     */
    private void renderAtomBones(Atom atom, boolean isDrawingText) {
        for ( int i = 1; i < atom.getBones().length; i++ ) {
            drawLine(
                    (int)atom.getBones()[i].getPointA().getX(), (int)atom.getBones()[i].getPointA().getY(),
                    (int)atom.getBones()[i].getPointB().getX(), (int)atom.getBones()[i].getPointB().getY(),
                    atom.getColor()
            );

            if ( isDrawingText ) {
                drawText("" + i,
                        (int)atom.getBones()[i].getPointB().getX(),
                        (int)atom.getBones()[i].getPointB().getY(),
                        atom.getId() % 2 == 0 ? HexColors.MINT : HexColors.DARK_MINT);

            }
        }
    }

    /**
     * This method draws the bones of the atom passed by parameter
     * In first place, the bones are sorted by its Z value of point B
     *
     * @param atom the atom whose bones is want to drawn
     */
    private void renderBones(Atom atom) {
        ArrayList<Bone> bones = new ArrayList<>(Arrays.asList(atom.getBones()));

        bones.sort(((o1, o2) -> Float.compare(o2.getPointB().getZ(), o1.getPointB().getZ())));

        if ( isDrawingRadius ) {
            renderRadiusBone(atom);
        }

        renderAtomBones(atom, isDrawingBonesTexts);
    }

    private void renderAtom(Atom atom) {
        if ( isDrawingAtomImages ) {
            Vec4df point = atom.getBones()[1].getPointA();
            Transform2D transform = new Transform2D();
            float z = 1.0f / point.getZ();
            transform.scale(z, z);
            transform.scale(atom.getBones()[0].distance2d() / 50, atom.getBones()[0].distance2d() / 50);
            transform.translate(- atom.getImage().getW() / 2.0f, - atom.getImage().getH() / 2.0f);
            transform.translate(point.getX(), point.getY());
            drawImage(atom.getImage(), transform);
        }
        if ( isDrawingBones ) {
            renderBones(atom);
        }
        if ( isDrawingAtomsIndex ) {
            drawText(
                    String.format("@%d", atom.getId()),
                    (int)atom.getBones()[1].getPointA().getX(),
                    (int)atom.getBones()[1].getPointA().getY(),
                    HexColors.LIGHT_BLUE);
        }
    }

    /**
     * This method draws all atoms passed by parameter
     *
     * @param atoms the ArrayList which contains all the Atoms
     */
    public void renderAtoms(ArrayList<Atom> atoms) {
        for ( Atom atom : atoms ) {
            renderAtom(atom);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isDrawingAtomImages() {
        return isDrawingAtomImages;
    }

    public boolean isDrawingBones() {
        return isDrawingBones;
    }

    public boolean isDrawingRadius() {
        return isDrawingRadius;
    }

    public boolean isDrawingBonesTexts() {
        return isDrawingBonesTexts;
    }

    public boolean isDrawingAtomsIndex() {
        return isDrawingAtomsIndex;
    }

    public void setDrawingAtomImages(boolean drawingAtomImages) {
        isDrawingAtomImages = drawingAtomImages;
    }

    public void setDrawingBones(boolean drawingBones) {
        isDrawingBones = drawingBones;
    }

    public void setDrawingRadius(boolean drawingRadius) {
        isDrawingRadius = drawingRadius;
    }

    public void setDrawingBonesTexts(boolean drawingBonesTexts) {
        isDrawingBonesTexts = drawingBonesTexts;
    }

    public void setDrawingAtomsIndex(boolean drawingAtomsIndex) {
        isDrawingAtomsIndex = drawingAtomsIndex;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
