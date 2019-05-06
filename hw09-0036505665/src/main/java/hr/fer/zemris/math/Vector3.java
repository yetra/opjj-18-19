package hr.fer.zemris.math;

public class Vector3 {

    public Vector3(double x, double y, double z) {

    } // konstruktor

    public double norm() {
        return 0.0;
    } // norma vektora (“duljina”)

    public Vector3 normalized() {
        return null;
    } // normalizirani vektor

    public Vector3 add(Vector3 other) {
        return null;
    } // zbrajanje

    public Vector3 sub(Vector3 other) {
        return null;
    } // oduzimanje: ja minus drugi

    public double dot(Vector3 other) {
        return 0.0;
    } // skalarni produkt

    public Vector3 cross(Vector3 other) {
        return null;
    } // vektorski produkt: ja puta on

    public Vector3 scale(double s) {
        return null;
    } // skaliranje zadanim faktorom

    public double cosAngle(Vector3 other) {
        return 0.0;
    } // kosinus kuta između mene i njega

    public double getX() {
        return 0.0;
    } // prva komponenta vektora

    public double getY() {
        return 0.0;
    } // druga komponenta vektora

    public double getZ() {
        return 0.0;
    } // treća komponenta vektora

    public double[] toArray() {
        return null;
    } // pretvorba u polje s 3 elementa

    public String toString() {
        return null;
    } // pretvorba u string
}
