package molecule;

import engine3d.Vec4df;

/**
 * This class represents a set of two points
 * which conforms a bone
 *
 * A bone is the minor object what can be represented
 * in the Atom PipeLine
 *
 * @author Sergio Martí Torregrosa. sMartiTo97
 * @date 22/09/2020
 */
public class Bone {

    /**
     * The point A
     */
     private Vec4df pointA;

    /**
     * The point B
     */
     private Vec4df pointB;

    /**
     * If the point A is connected
     * by default, true
     */
     private boolean isPointAConnected = true;

    /**
     * If the point B is connected
     * by default, false
     */
     private boolean isPointBConnected = false;

    /**
     * Null constructor
     */
     public Bone() {
         pointA = new Vec4df();
         pointB = new Vec4df();
     }

    /**
     * The constructor
     *
     * @param pointA the point A of the bone
     * @param pointB the point B of the bone
     */
     public Bone(Vec4df pointA, Vec4df pointB) {
         this.pointA = pointA;
         this.pointB = pointB;
     }

    /**
     * Full constructor with all specified fields
     *
     * @param pointA the point A of the bone
     * @param pointB the point B of the bone
     * @param isPointAConnected if the point A is connected
     * @param isPointBConnected if the point B is connected
     */
    public Bone(Vec4df pointA, Vec4df pointB, boolean isPointAConnected, boolean isPointBConnected) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.isPointAConnected = isPointAConnected;
        this.isPointBConnected = isPointBConnected;
    }

    /**
     * The copy constructor
     *
     * @param bone the bone from is going to copy all data
     */
    public Bone(Bone bone) {
         this.pointA = new Vec4df(bone.getPointA());
         this.pointB = new Vec4df(bone.getPointB());
         this.isPointAConnected = bone.isPointAConnected();
         this.isPointBConnected = bone.isPointBConnected();
    }

    /**
     * This method calculates the distance between the two points
     * of the bone only tacking into account the X and Y coordinates,
     * working as 2d space
     *
     * @return the 2d distance
     */
    public float distance2d() {
        return (float) Math.sqrt(
                (pointA.getX() - pointB.getX()) * (pointA.getX() - pointB.getX()) +
                        (pointA.getY() - pointB.getY()) * (pointA.getY() - pointB.getY())
        );
    }

    /**
     * This method calculates the distance between the two points
     * of the bone tacking into account the X, Y and Z coordinates,
     * working as 3d space
     *
     * @return the 3d distance
     */
    public float distance3d() {
        return (float) Math.sqrt(
                (pointA.getX() - pointB.getX()) * (pointA.getX() - pointB.getX()) +
                        (pointA.getY() - pointB.getY()) * (pointA.getY() - pointB.getY()) +
                        (pointA.getZ() - pointB.getZ()) * (pointA.getZ() - pointB.getZ())
        );
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Vec4df getPointA() {
        return pointA;
    }

    public Vec4df getPointB() {
        return pointB;
    }

    public boolean isPointAConnected() {
        return isPointAConnected;
    }

    public boolean isPointBConnected() {
        return isPointBConnected;
    }

    public void setPointA(Vec4df pointA) {
        this.pointA = pointA;
    }

    public void setPointB(Vec4df pointB) {
        this.pointB = pointB;
    }

    public void setPointAConnected(boolean isPointAConnected) {
        this.isPointAConnected = isPointAConnected;
    }

    public void setPointBConnected(boolean isPointBConnected) {
        this.isPointBConnected = isPointBConnected;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "A " + pointA.toString() + (isPointAConnected ? " T" : " F") +
                " B " + pointB.toString() + (isPointBConnected ? " T" : " F");
    }
}
