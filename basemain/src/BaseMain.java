public class BaseMain {
    

    public static void main(String argd[]){
        
    Base b = new Sub();

    Sub s = (Sub) b;
    s.setId(5);

    System.out.println(s.getId());
    }
    
}


class Base
{
    protected int id = 0;
    public int getId()
    {
        return id;
    }
}

class Sub extends Base
{
    public void setId(int nid)
    {
        id = nid;
    }
}