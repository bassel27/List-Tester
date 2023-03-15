import java.util.Arrays;

public class Bruteforce {
    public static void main(String[] args) {
        new Bruteforce().generate("abc"); // Display the string.
    }

    public void generate(char[] input) {
        char[] result = new char[input.length];
        int[] index = new int[input.length];

        // initialize the arrays to zero
        Arrays.fill(result, 0, result.length, input[0]); // filled with first char in input
        Arrays.fill(index, 0, index.length, 0); // filled with zero
        

        // loop over the output lengths.
        for (int length = 1; length <= input.length; length++) {
            int updateIndex = 0;
            do {
                // update values that need to reset.
                for (updateIndex = length - 1; updateIndex != -1
                        && ++index[updateIndex] == input.length; result[updateIndex] = input[0], index[updateIndex] = 0, updateIndex--)
                    ;

                // update the character that is not resetting, if valid
                if (updateIndex != -1)
                    result[updateIndex] = input[index[updateIndex]];
            } while (updateIndex != -1);
            for(char r: result){
                System.out.println(r);
            }
        }
    }

    public void generate(String input) {
        generate(input.toCharArray());
    }

}
