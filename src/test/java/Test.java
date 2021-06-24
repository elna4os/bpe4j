import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class Test extends TestCase {
    public void testBasic() {
        List<String> data = Arrays.asList(
                "I want to be an actor, Dad. I want to go on stage.",
                "You should be a banker, Tom, and earn a decent wage.",
                "I want to be a barber, Dad. I want to do people’s hair.",
                "You should be a pilot Tom, and work for British Air.",
                "I want to be a clown, Dad. I think I am very funny.",
                "You should be a driver, Tom, and earn a lot of money.",
                "I want to be a barman, Dad, so I can drink a lot of beer!",
                "A barman! You are joking, Tom. That isn’t a career!",
                "I want to go into politics, Dad, and put the country right.",
                "I think that’s an excellent idea.",
                "Let’s tell your mother tonight."
        );
        BytePairEncoding bpe = new BytePairEncoding(
                data,
                100,
                100,
                "<w>",
                "</w>"
        );
        String[] tokens = bpe.tokenize("I want to ride a bicycle");
        System.out.println("I want to be an actor, Dad. I want to go on stage.");
        System.out.println(Arrays.toString(tokens));
    }
}
