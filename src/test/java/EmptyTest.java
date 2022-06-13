public class EmptyTest {
    public static void main(String[] args) {
        String str = null;
        if(str ==null){
            System.out.println("null");
        }
        str="";
        if (str.isEmpty()) {
            System.out.println("Empty");
        }
        str=" ";
        if(str.isBlank()){
            System.out.println("Blank");
        }
    }
}
