import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger count3 = new AtomicInteger();
    private static AtomicInteger count4 = new AtomicInteger();
    private static AtomicInteger count5 = new AtomicInteger();

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String text : texts) {
                if (isPalindrom(text)) {
                    counterIncrease(text);
                }
            }
        }).start();
        new Thread(() -> {
            for (String text : texts) {
                if (isOneCharacter(text)) {
                    counterIncrease(text);
                }
            }
        }).start();
        new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text)) {
                    counterIncrease(text);
                }
            }
        }).start();

        System.out.println("Красивых слов с длиной 3: " + count3 + " шт\n" +
                "Красивых слов с длиной 4: " + count4 + " шт\n" +
                "Красивых слов с длиной 5: " + count5 + " шт\n");

    }

    private static void counterIncrease(String text) {
        switch (text.length()) {
            case 3 -> count3.getAndIncrement();
            case 4 -> count4.getAndIncrement();
            case 5 -> count5.getAndIncrement();
            default -> throw new IllegalStateException("Unexpected value: " + text.length());
        }
    }

    public static boolean isPalindrom(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isOneCharacter(String text) {
        boolean tmp = true;
        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j < text.length(); j++) {
                if (text.charAt(i) != text.charAt(j)) {
                    tmp = false;
                    break;
                }
            }
        }
        return tmp;
    }

    public static boolean isAscending(String text) {
        boolean tmp = true;
        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j < text.length(); j++) {
                if (text.charAt(i) > text.charAt(j)) {
                    tmp = false;
                    break;
                }
            }
        }
        return tmp;
    }

}
