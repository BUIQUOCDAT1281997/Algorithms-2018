package lesson1;

public class thuNghiem {
    public int ucll(int a, int b){
        if (b==0) return a;
        else {
            a=b;
            b= a%b;
            return ucll(a, b);
        }
    }
    public static void main(String[] args) {
        System.out.print(new thuNghiem().ucll(7,5));
    }
}
