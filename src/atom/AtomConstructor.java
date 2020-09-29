package atom;

import engine.gfx.HexColors;
import engine.gfx.images.Image;
import engine.gfx.images.ImageTile;
import engine3d.Vec4df;
import engine3d.matrix.Mat4x4;
import engine3d.matrix.MatrixMath;

/**
 * Thi class is an @Atom object constructor what
 * contains all methods to build atoms.
 *
 * @author Sergio Martí Torregrosa. sMartiTo97
 * @date 08/09/2020
 */
public class AtomConstructor {

    private static ImageTile atomTiles = new ImageTile("/atoms_tiles.png", 64, 64);

    private static Vec4df[] scalePoints(Vec4df[] points, float radius) {
        Mat4x4 matScale = MatrixMath.matrixMakeScale(radius, radius, radius);
        for ( int i = 0; i < points.length; i++ ) {
            points[i] = MatrixMath.matrixMultiplyVector(matScale, points[i]);
        }
        return points;
    }

    private static Vec4df[] buildSp3Points(float radius) {
        Vec4df[] points = new Vec4df[5];
        points[0] = new Vec4df();
        points[1] = new Vec4df(0.0f, 1.0f, 0.0f);
        points[2] = new Vec4df(-0.47140f, -0.33333f, -0.81650f);
        points[3] = new Vec4df(0.94281f, -0.33333f, 0.00000f);
        points[4] = new Vec4df(-0.47140f, -0.33333f, 0.81650f);
        return scalePoints(points, radius);
    }

    private static Vec4df[] buildSp2Points(float radius) {
        Vec4df[] points = new Vec4df[4];
        points[0] = new Vec4df();
        points[1] = new Vec4df(0.0f, 1.0f, 0.0f);
        points[2] = new Vec4df(0.866025f, -0.5f, 0.0f);
        points[3] = new Vec4df(-0.866025f, -0.5f, 0.0f);
        return scalePoints(points, radius);
    }

    private static Vec4df[] buildSpPoints(float radius) {
        Vec4df[] points = new Vec4df[3];
        points[0] = new Vec4df();
        points[1] = new Vec4df(0.0f, radius, 0.0f);
        points[2] = new Vec4df(0.0f, -radius, 0.0f);
        return scalePoints(points, radius);
    }

    private static Vec4df[] getPoints(Hybridization hybridization, float radius) {
        switch ( hybridization ) {
            case SP3: default:
                return buildSp3Points(radius);
            case SP2:
                return buildSp2Points(radius);
            case SP:
                return buildSpPoints(radius);
        }
    }

    private static Image getAtomImage(AtomKind atomKind) {
        switch ( atomKind ) {
            case CARBON: default:
                return atomTiles.getTileImage(0, 0);
            case HYDROGEN:
                return atomTiles.getTileImage(1, 0);
            case OXYGEN:
                return atomTiles.getTileImage(2, 0);
            case NITROGEN:
                return atomTiles.getTileImage(3, 0);
            case PHOSPHOR:
                return atomTiles.getTileImage(4, 0);
            case SULFUR:
                return atomTiles.getTileImage(5, 0);
        }
    }

    private static float getAtomRadius(AtomKind atomKind) {
        switch ( atomKind ) {
            case CARBON: case OXYGEN: case NITROGEN: default:
                return 1.0f;
            case HYDROGEN:
                return 0.5f;
            case SULFUR: case PHOSPHOR:
                return 1.5f;
        }
    }

    private static int getColor(AtomKind atomKind) {
        switch ( atomKind ) {
            case CARBON: default:
                return HexColors.GREY;
            case HYDROGEN:
                return HexColors.WHITE;
            case OXYGEN:
                return HexColors.WINE;
            case NITROGEN:
                return HexColors.ROYAL_BLUE;
            case PHOSPHOR:
                return HexColors.TANGERINE;
            case SULFUR:
                return HexColors.LEMON;
        }
    }

    public static Atom buildAtom(int id, float radius, AtomKind atomKind, Hybridization hybridization) {
        return new Atom(id, radius, getPoints(hybridization, radius), getAtomImage(atomKind), atomKind, getColor(atomKind));
    }

    public static Atom buildAtom(int id, AtomKind atomKind, Hybridization hybridization) {
        return buildAtom(id, getAtomRadius(atomKind), atomKind, hybridization);
    }

    public static Atom buildAtom(AtomKind atomKind, Hybridization hybridization) {
        return buildAtom(0, getAtomRadius(atomKind), atomKind, hybridization);
    }

}
