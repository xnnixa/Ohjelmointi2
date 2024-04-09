public class AgeException {

    static class NegativeAgeException extends Exception {
        public NegativeAgeException(String message) {
            super(message);
        }
    }

    static class Person {
        private String name;
        private int age;

        public Person(String iname, int iage) throws NegativeAgeException {
            if (iage < 0) {
                throw new NegativeAgeException("Negative age not allowed. ");
            }
            name = iname;
            age = iage;
        }

        public String getName() {
            return name;
        }

        public void setName(String nname) {
            name = nname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int nage) throws NegativeAgeException {
            if (nage < 0) {
                throw new NegativeAgeException("Negative age not allowed. ");
            }
            age = nage;
        }
    }
    
    // DON'T TOUCH THE MAIN FUNCTION!!
    public static void main(String args[]) throws NegativeAgeException{
        // DON'T TOUCH THE MAIN FUNCTION!!
        try {
            Person human = new Person("asd" ,1 );
        } catch (NegativeAgeException e) {
            System.out.println(e.toString() + 1);
        }
        // DON'T TOUCH THE MAIN FUNCTION!!
        try {
            Person anotherHuman = new Person("asd", -2);
        } catch (NegativeAgeException e) {
            System.out.println(e.toString() + 2);
        }// DON'T TOUCH THE MAIN FUNCTION!!
    
    }// DON'T TOUCH THE MAIN FUNCTION!!// DON'T TOUCH THE MAIN FUNCTION!!
}