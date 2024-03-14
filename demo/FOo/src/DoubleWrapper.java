
class DoubleWrapper
{
    private double val = 0;

    public void setVal(int newval) {
        val = newval;
    }

    public void setVal(double newval) {
        val = newval;
    }

    public void setVal(String newval) {
        val = Double.parseDouble(newval);
    }
}