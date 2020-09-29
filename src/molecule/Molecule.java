package molecule;

import atom.Atom;
import engine3d.Vec4df;
import engine3d.matrix.Mat4x4;
import engine3d.matrix.MatrixMath;

import java.util.ArrayList;

/**
 * This class represents a Molecule
 * A molecule is a set of joined atoms
 *
 * @author Sergio Martí Torregrosa. sMartiTo97
 * @date 22/09/2020
 */
public class Molecule {

    /**
     * The atoms which conform the molecule
     */
    private ArrayList<Atom> atoms;

    /**
     * The constructor
     *
     * An Atom is request because it don't have any sense
     * a molecule conformed by none atom
     *
     * @param atom the first atom of the molecule
     */
    public Molecule(Atom atom) {
        atoms = new ArrayList<>();
        addAtom(atom);
    }

    /**
     * This method only adds the atom passed by parameter
     * to the ArrayList of atoms and sets its id
     *
     * @param atom the new atom for the molecule
     */
    public void addAtom(Atom atom) {
        atom.setId(atoms.size());
        atoms.add(atom);
    }

    /**
     * This method adds the atom passed by parameter to
     * the link of a present atom on molecule which matches with
     * the specified id
     *
     * @param idAtom the id of a present atom in the molecule
     * @param atom the atom which is wanted to join
     * @param linkAtom1 the link of the present atom, where join the new atom
     */
    public void addAtom(int idAtom, Atom atom, int linkAtom1) {
        Atom presentAtom = atoms.get(idAtom);

        rotateAtom(presentAtom, atom);
        translateAtom(atom, presentAtom.getBones()[linkAtom1].getPointB());

        presentAtom.getBones()[linkAtom1].setPointBConnected(true);
        atom.getBones()[linkAtom1].setPointBConnected(true);

        addAtom(atom);
        /*if ( !presentAtom.getBones()[linkAtom1].isPointBConnected() ) {
            rotateAtom(presentAtom, atom);
            translateAtom(atom, presentAtom.getBones()[linkAtom1].getPointB());
            presentAtom.getBones()[linkAtom1].setPointBConnected(true);
            atom.getBones()[linkAtom1].setPointBConnected(true);
            addAtom(atom);
        } else {
            System.out.println("Error: no se puede unir el átomo porque ese orbital de enlace ya esta ocupado");
        }*/
    }

    /**
     * This method rotates the atom2 the amount of rotation
     * of atom1. After, sets the value of rotation field of the
     * atom2 to the value of rotation from atom1 increased in 180 degrees
     *
     * The rotation is stored as degrees, for this reason, is needed
     * the conversion to radians ( degrees x Pi / 180 = radians )
     *
     * @param atom1 the first atom
     * @param atom2 the second atom
     */
    private void rotateAtom(Atom atom1, Atom atom2) {
        rotateAtomZ(atom2, (float)(atom1.getRotation() * (Math.PI / 180.0f)));
        atom2.setRotation(atom1.getRotation() + 180.0f);
    }

    /**
     * This method transforms the all the bones which conform the atom
     * (the radius bone and normal bones) by the angle in radians
     * passed by parameter
     *
     * @param atom the atom to rotate
     * @param angleRadZ the angle of rotation
     */
    private void rotateAtomZ(Atom atom, float angleRadZ) {
        Mat4x4 rotZ = MatrixMath.matrixMakeRotationZ((float)(Math.PI - angleRadZ));
        atom.setBones(MatrixMathAtom.matrixMultiplyBones(rotZ, atom.getBones()));
    }

    /**
     * This method translates all the bones of the atom by the
     * position passed by parameter
     *
     * @param atom the atom to translate
     * @param translate the position to translate
     */
    private void translateAtom(Atom atom, Vec4df translate) {
        for (int i = 0; i < atom.getBones().length; i++ ) {
            atom.getBones()[i].setPointA(MatrixMathAtom.vectorAdd(atom.getBones()[i].getPointA(), translate));
            atom.getBones()[i].setPointB(MatrixMathAtom.vectorAdd(atom.getBones()[i].getPointB(), translate));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Atom> getAtoms() {
        return atoms;
    }

    public void setAtoms(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
