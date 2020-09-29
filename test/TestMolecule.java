import atom.AtomConstructor;
import atom.AtomKind;
import atom.AtomsPipeLine;
import atom.Hybridization;
import engine.AbstractGame;
import engine.GameContainer;
import engine.gfx.Renderer;
import engine3d.Vec4df;
import engine3d.matrix.Mat4x4;
import engine3d.matrix.MatrixMath;
import molecule.Molecule;

import java.awt.event.KeyEvent;

public class TestMolecule extends AbstractGame {

    private AtomsPipeLine pipeLine;

    private Molecule molecule;

    private Vec4df cubeTranslation = new Vec4df();

    private Vec4df moleculeRotation = new Vec4df();

    private TestMolecule(String title) {
        super(title);
    }

    @Override
    public void initialize(GameContainer gc) {
        pipeLine = new AtomsPipeLine(gc);

        molecule = new Molecule(AtomConstructor.buildAtom(AtomKind.CARBON, Hybridization.SP3));

        molecule.addAtom(0, AtomConstructor.buildAtom(AtomKind.CARBON, Hybridization.SP3), 3);
        molecule.addAtom(1, AtomConstructor.buildAtom(AtomKind.CARBON, Hybridization.SP3), 4);
        molecule.addAtom(2, AtomConstructor.buildAtom(AtomKind.CARBON, Hybridization.SP3), 4);
        molecule.addAtom(3, AtomConstructor.buildAtom(AtomKind.CARBON, Hybridization.SP3), 3);
        molecule.addAtom(4, AtomConstructor.buildAtom(AtomKind.CARBON, Hybridization.SP3), 1);
        molecule.addAtom(4, AtomConstructor.buildAtom(AtomKind.OXYGEN, Hybridization.SP3), 2);

        molecule.addAtom(0, AtomConstructor.buildAtom(AtomKind.OXYGEN, Hybridization.SP3), 2);
        molecule.addAtom(1, AtomConstructor.buildAtom(AtomKind.OXYGEN, Hybridization.SP3), 1);
        molecule.addAtom(2, AtomConstructor.buildAtom(AtomKind.OXYGEN, Hybridization.SP3), 1);
        molecule.addAtom(3, AtomConstructor.buildAtom(AtomKind.OXYGEN, Hybridization.SP3), 4);
        molecule.addAtom(5, AtomConstructor.buildAtom(AtomKind.OXYGEN, Hybridization.SP3), 3);

        molecule.addAtom(1, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 2);
        molecule.addAtom(2, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 3);
        molecule.addAtom(3, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 1);

        molecule.addAtom(7, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 3);
        molecule.addAtom(0, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 1);
        molecule.addAtom(8, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 2);
        molecule.addAtom(9, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 2);
        molecule.addAtom(10, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 3);
        molecule.addAtom(4, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 4);

        molecule.addAtom(5, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 2);
        molecule.addAtom(5, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 4);

        molecule.addAtom(11, AtomConstructor.buildAtom(AtomKind.HYDROGEN, Hybridization.SP3), 2);

    }

    private void updateCameraPanning(GameContainer gc, float dt) {
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_RIGHT) ) {
            pipeLine.getCameraObj().getOrigin().addToX(2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_LEFT) ) {
            pipeLine.getCameraObj().getOrigin().addToX(-2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_UP) ) {
            pipeLine.getCameraObj().getOrigin().addToY(2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_DOWN) ) {
            pipeLine.getCameraObj().getOrigin().addToY(-2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_Z) ) {
            pipeLine.getCameraObj().getOrigin().addToZ(2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_X) ) {
            pipeLine.getCameraObj().getOrigin().addToZ(-2.0f * dt);
        }
    }

    private void updateCameraRotation(GameContainer gc, float dt) {
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_W) ) {
            pipeLine.getCameraObj().rotX(-2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_S) ) {
            pipeLine.getCameraObj().rotX(2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_A) ) {
            pipeLine.getCameraObj().rotY(-2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_D) ) {
            pipeLine.getCameraObj().rotY(2.0f * dt);
        }
    }

    private void updateCameraZoom(GameContainer gc, float dt) {
        Vec4df forward = MatrixMath.vectorMul(pipeLine.getCameraObj().getLookDirection(), - gc.getInput().getScroll() * 0.5f * dt);
        pipeLine.getCameraObj().setOrigin(MatrixMath.vectorAdd(pipeLine.getCameraObj().getOrigin(), forward));
    }

    private void updateCamera(GameContainer gc, float dt) {
        updateCameraPanning(gc, dt);
        updateCameraRotation(gc, dt);
        updateCameraZoom(gc, dt);
        pipeLine.setMatView(pipeLine.getCameraObj().getMatView());
    }

    private void updateMolecule(GameContainer gc, float dt) {
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_NUMPAD8) ) {
            moleculeRotation.addToX(2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_NUMPAD2) ) {
            moleculeRotation.addToX(-2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_NUMPAD4) ) {
            moleculeRotation.addToY(2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_NUMPAD6) ) {
            moleculeRotation.addToY(-2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_NUMPAD7) ) {
            moleculeRotation.addToZ(2.0f * dt);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_NUMPAD3) ) {
            moleculeRotation.addToZ(-2.0f * dt);
        }
    }

    public void updateAtomsRenderer(GameContainer gc) {
        if ( gc.getInput().isKeyDown(KeyEvent.VK_I) ) {
            pipeLine.getRenderer().setDrawingAtomImages(!pipeLine.getRenderer().isDrawingAtomImages());
        }
        if ( gc.getInput().isKeyDown(KeyEvent.VK_B) ) {
            pipeLine.getRenderer().setDrawingBones(!pipeLine.getRenderer().isDrawingBones());
        }
        if ( gc.getInput().isKeyDown(KeyEvent.VK_R) ) {
            pipeLine.getRenderer().setDrawingRadius(!pipeLine.getRenderer().isDrawingRadius());
        }
        if ( gc.getInput().isKeyDown(KeyEvent.VK_T) ) {
            pipeLine.getRenderer().setDrawingBonesTexts(!pipeLine.getRenderer().isDrawingBonesTexts());
        }
        if ( gc.getInput().isKeyDown(KeyEvent.VK_J) ) {
            pipeLine.getRenderer().setDrawingAtomsIndex(!pipeLine.getRenderer().isDrawingAtomsIndex());
        }
    }

    private void transformMolecule() {
        Mat4x4 matIdentity = MatrixMath.matrixMakeIdentity();
        Mat4x4 matRotX = MatrixMath.matrixMakeRotationX(moleculeRotation.getX());
        matRotX = MatrixMath.matrixMultiplyMatrix(matIdentity, matRotX);
        Mat4x4 matRotY = MatrixMath.matrixMakeRotationY(moleculeRotation.getY());
        Mat4x4 matRotXY = MatrixMath.matrixMultiplyMatrix(matRotY, matRotX);
        Mat4x4 matRotZ = MatrixMath.matrixMakeRotationZ(moleculeRotation.getZ());
        Mat4x4 matRot = MatrixMath.matrixMultiplyMatrix(matRotXY, matRotZ);
        Mat4x4 matTranslation = MatrixMath.matrixMakeTranslation(cubeTranslation.getX(), cubeTranslation.getY(), cubeTranslation.getZ());
        Mat4x4 matRotTrans = MatrixMath.matrixMultiplyMatrix(matRot, matTranslation);
        pipeLine.setTransform(matRotTrans);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        updateCamera(gc, dt);
        updateMolecule(gc, dt);
        updateAtomsRenderer(gc);
        transformMolecule();
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        pipeLine.renderMolecule(molecule);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new TestMolecule("Test Molecule"));
        gc.start();
    }

}
