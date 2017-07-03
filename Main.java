package simulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> inputs = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        if(line != null && !line.isEmpty()) {
            int res = resolve(line.trim());
            System.out.println(String.valueOf(res));
        }
    }

    // write your code here
    public static int resolve(String expr) {
       int res = 0;
       Stack<Integer> stack = new Stack<Integer>();
       String[] s = expr.split(" ");
       for (int i = 0; i < s.length; i++) {
           if (s[i] != " ") {
              if (s[i].charAt(0) == '^') {
                if (stack.isEmpty()) {
                   return -1;
                 }
                int tmp = stack.pop();
                stack.push(++tmp);
                
              } else if (s[i].charAt(0) == '*') {
               if (stack.isEmpty()) {
                 return -1;
               }
                int t1 = stack.pop();
                if (stack.isEmpty()) {
                 return -1;
               }
                int t2 =  stack.pop();
                stack.push(t1 * t2);
              } else if (s[i].charAt(0) == '+') {
                if (stack.isEmpty()) {
                 return -1;
               }
                int t1 = stack.pop();
                if (stack.isEmpty()) {
                 return -1;
               }
                int t2 = stack.pop();
                  stack.push(t1 + t2);
               } else {
               int tmp = Integer.parseInt(s[i]);
                if (stack.size() == 16) {
                   return -2;
                }
                stack.push(tmp);
               }
           }
       }
      if (stack.isEmpty()) {
        return -1;
      }
       return stack.pop();
    }
}
