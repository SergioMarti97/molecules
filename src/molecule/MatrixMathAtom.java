package molecule;

import engine3d.matrix.Mat4x4;
import engine3d.matrix.MatrixMath;

/**
 * This class has all the methods for project the atoms
 */
public class MatrixMathAtom extends MatrixMath {

    public static Bone matrixMultiplyBone(Mat4x4 mat4x4, Bone bone) {
        return new Bone(
                matrixMultiplyVector(mat4x4, bone.getPointA()),
                matrixMultiplyVector(mat4x4, bone.getPointB()),
                bone.isPointAConnected(),
                bone.isPointBConnected()
        );
    }

    public static Bone[] matrixMultiplyBones(Mat4x4 mat4x4, Bone[] bones) {
        Bone[] bonesTransformed = new Bone[bones.length];
        for ( int i = 0; i < bones.length; i++ ) {
            bonesTransformed[i] = matrixMultiplyBone(mat4x4, bones[i]);
        }
        return bonesTransformed;
    }

}
