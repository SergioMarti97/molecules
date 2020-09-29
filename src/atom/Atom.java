package atom;

import engine.gfx.images.Image;
import engine3d.Vec4df;
import molecule.Bone;

/**
 * This class represents an Atom
 *
 * An atom is the smallest portion of a chemical element that takes part in
 * chemical reactions and possesses the characteristic properties of said element
 *
 * In this approach, an atom is represented by a set of bones (class @bone)
 *
 * @author Sergio Martí Torregrosa. sMartiTo97
 * @date 08/09/2020
 */
public class Atom {

    /**
     * The id to identify the atom
     */
    private int id;

    /**
     * The radius of the atom
     */
    private float radius;

    /**
     * The color of the atom
     */
    private int color;

    /**
     * An image what represents the atom kind
     */
    private Image image;

    /**
     * The bones which forms the atom
     */
    private Bone[] bones;

    /**
     * The type of chemical element
     */
    private AtomKind atomKind;

    /**
     * The rotation of the atom
     */
    private float rotation = 0.0f;

    /**
     * The Atom constructor
     * @param id the identify code
     * @param radius the radius
     * @param p the points
     * @param image the image of the atom
     * @param atomKind the type of chemical element
     */
    public Atom(int id, float radius, Vec4df[] p, Image image, AtomKind atomKind, int color) {
        this.id = id;
        this.radius = radius;
        bones = buildBones(p);
        this.image = image;
        this.atomKind = atomKind;
        this.color = color;
    }

    /**
     * The Atom constructor
     * @param id the identify code
     * @param radius the radius
     * @param bones the bones
     * @param image the image of the atom
     * @param atomKind the type of chemical element
     */
    public Atom(int id, float radius, Bone[] bones, Image image, AtomKind atomKind, int color) {
        this.id = id;
        this.radius = radius;
        this.bones = bones;
        this.image = image;
        this.atomKind = atomKind;
        this.color = color;
    }

    /**
     * The copy constructor
     * @param atom the atom to copy
     */
    public Atom(Atom atom) {
        this.id = atom.getId();
        this.radius = atom.getRadius();
        this.bones = atom.getBones();
        this.image = atom.getImage();
        this.atomKind = atom.getAtomKind();
        this.rotation = atom.getRotation();
        this.color = atom.getColor();
    }

    /**
     * This method builds a set of bones from
     * the points passed as a parameter
     *
     * The atom will always have one extra bone,
     * which will be the first (index 0). This
     * extra bone will be the needed for calculate
     * the projected radius of the atom
     *
     * As the points passed by parameter will have
     * always one more item than the necessary bone
     * array, but is needed one extra bone, so the
     * two arrays will have the same length
     *
     * But this extra bone will be secret
     *
     * @param p the points
     * @return return a set of bones
     */
    private Bone[] buildBones(Vec4df[] p) {
        Bone[] bones = new Bone[p.length];

        Vec4df pointRadius = new Vec4df(p[0]);
        pointRadius.addToX(radius);
        bones[0] = new Bone(p[0], pointRadius);

        for ( int i = 1; i < p.length; i++ ) {
            bones[i] = new Bone(p[0], p[i]);
        }
        return bones;
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    public int getId() {
        return id;
    }

    public float getRadius() {
        return radius;
    }

    public Image getImage() {
        return image;
    }

    public float getRotation() {
        return rotation;
    }

    public Bone[] getBones() {
        return bones;
    }

    public AtomKind getAtomKind() {
        return atomKind;
    }

    public int getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setBones(Bone[] bones) {
        this.bones = bones;
    }

    public void setAtomKind(AtomKind atomKind) {
        this.atomKind = atomKind;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        return atomKind.toString() + " " + id;
    }

}
