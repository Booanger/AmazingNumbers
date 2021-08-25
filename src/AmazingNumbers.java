import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;


public class AmazingNumbers {
    private static long one = -1, two;
    private static HashMap<String, Boolean> threePlus;
    private static NumberFormat myFormat;

    public static void main(String[] args) {
        boolean isExit = false;
        Scanner input = new Scanner(System.in);
        myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        System.out.println("""
                Welcome to Amazing Numbers!
                                
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.""");
        while (!isExit) {

            System.out.print("\nEnter a request: ");
            //String[] line = {"1", "5", "odd", "palindromic", "gapful"};
            String[] line = input.nextLine().split(" ");
            boolean isValid = isInputValid(line); //checks if input parameters are in supported format

            if (!isValid) {
                continue;
            }

            if (one == 0) {
                System.out.println("\nGoodbye!");
                isExit = true;
                continue;
            }

            switch (line.length) {
                case 1 -> numberProperties(one);
                case 2 -> numberProperties(one, two);
                default -> numberProperties(one, two, threePlus);
            }
        }
    }

    static boolean isBuzzNumber(long number) {
        return number % 10 == 7 || number % 7 == 0;
    }

    static boolean isDuckNumber(long number) {
        if (number != 0) return Long.toString(number).contains("0");
        return false;
    }

    static boolean isPalindromic(long number) {
        String temp = Long.toString(number);
        for (int i = 0; i < temp.length() / 2; i++) {
            if (temp.charAt(i) != temp.charAt(temp.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    static boolean isGapful(long number) {
        String temp = String.valueOf(number);
        if (number < 100) return false;

        String firstAndLast = temp.charAt(0) + String.valueOf(temp.charAt(temp.length() - 1));
        return number % Integer.parseInt(firstAndLast) == 0;
    }

    static boolean isSpyNumber(long number) {
        int product = 1;
        int sum = 0;
        long lastDigit;
        while (number > 0) {
            lastDigit =  number % 10;
            sum += lastDigit;
            product *= lastDigit;
            number /= 10;
        }
        return product == sum;
    }

    static boolean isSquareNumber(long number) {
        double a = Math.sqrt(number);
        return ((a - Math.floor(a)) == 0);
    }

    static boolean isSunnyNumber(long number) {
        return isSquareNumber(number + 1);
    }

    static boolean isJumpingNumber(long number) {
        String temp = Long.toString(number);
        int[] digits = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++)
        {
            digits[i] = temp.charAt(i);
        }
        for (int i = 0; i < digits.length - 1; i++) {
            if (Math.abs(digits[i] - digits[i + 1]) != 1) {
                return false;
            }
        }
        return true;
    }

    static boolean isHappyNumber(long number) {
        if (number == 1) return true;
        Set<Integer> numberSet = new HashSet<>();

        int temp = numSquareSum(number);
        while (temp != 4) {
            temp = numSquareSum(temp);
            if (temp == 1) {
                return true;
            }
        }
        return false;
    }

    static int numSquareSum(int number) {
        int sum = 0;
        int rem = 0;
        while (number > 0) {
            rem = (number % 10);
            sum += (rem * rem);
            number /= 10;
        } return sum;
    }

    static int numSquareSum(long number) {
        int sum = 0;
        int rem = 0;
        while (number > 0) {
            rem = (int) (number % 10);
            sum += (rem * rem);
            number /= 10;
        } return sum;
    }

    static void numberProperties(long number) {
        if (number > 0) {
            boolean isEven = number % 2 == 0;
            System.out.printf("""
                                    
               Properties of %s
                       buzz: %b
                       duck: %b
                palindromic: %b
                     gapful: %b
                        spy: %b
                     square: %b
                      sunny: %b
                    jumping: %b
                      happy: %b
                        sad: %b
                       even: %b
                        odd: %b
                    """, myFormat.format(number), isBuzzNumber(number), isDuckNumber(number),
                    isPalindromic(number), isGapful(number), isSpyNumber(number),
                    isSquareNumber(number), isSunnyNumber(number), isJumpingNumber(number),
                    isHappyNumber(number), !isHappyNumber(number), isEven, !isEven);
        } else System.out.printf("%s is not natural", myFormat.format(number));
    }

    static void numberProperties(long number, long number2) {
        while (number2 > 0) {
            StringBuilder temp = new StringBuilder();
            if (isBuzzNumber(number)) temp.append("buzz, ");
            if (isPalindromic(number)) temp.append("palindromic, ");
            if (isGapful(number)) temp.append("gapful, ");
            // Spy number can NOT be a duck number
            if (isSpyNumber(number)) temp.append("spy, ");
            else if (isDuckNumber(number)) temp.append("duck, ");
            if (isJumpingNumber(number)) temp.append("jumping, ");
            //Square number can NOT be a sunny number
            if (isSquareNumber(number)) temp.append("square, ");
            else if (isSunnyNumber(number)) temp.append("sunny, ");
            //Happy number can NOT be a sad number
            if (isHappyNumber(number)) temp.append("happy, ");
            else temp.append("sad, ");
            if (number % 2 == 0) temp.append("even");
            else temp.append("odd");
            System.out.printf("%d is %s\n", number++, temp);
            //number++;
            number2--;
        }
    }

    static void numberProperties(long number, long number2, HashMap<String, Boolean> properties) {
        System.out.println();
        boolean valid;
        while (number2 > 0) {
            valid = true;
            for (String i: properties.keySet()) {
                if (!valid) {
                    break;
                }
                switch (i.toLowerCase()) {
                    case "spy":
                        if (isSpyNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        } break;
                    case "duck":
                        if (isDuckNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        } break;
                    case "buzz":
                        if (isBuzzNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        }
                        break;
                    case "palindromic":
                        if (isPalindromic(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        }
                        break;
                    case "gapful":
                        if (isGapful(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        }
                        break;
                    case "square":
                        if (isSquareNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        }
                        break;
                    case "sunny":
                        if (isSunnyNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        } break;
                    case "jumping":
                        if (isJumpingNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        } break;
                    case "happy":
                        if (isHappyNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        } break;
                    case "sad":
                        if (!isHappyNumber(number) ^ properties.get(i)) {
                            valid = false;
                            break;
                        } break;
                    case "even":
                        if (number % 2 == 0 ^ properties.get(i)) {
                            valid = false;
                            break;
                        }
                        break;
                    case "odd":
                        if (number % 2 == 1 ^ properties.get(i)) {
                                valid = false;
                                break;
                        }
                        break;
                }
            }
            if (valid) {
                numberProperties(number, 1);
                number++;
                number2--;
            } else {
                number++;
            }
        }
    }

    static boolean isInputValid(String[] input) {
        if (input.length == 1) {
            return isOneValid(input[0]);
        } else if (input.length == 2) {
            return isOneValid(input[0]) && isTwoValid(input[1]);
        } else  if (input.length >= 3) {
            List<String> temp = Arrays.asList(input);

            if (temp.contains("even") && temp.contains("odd") || temp.contains("-odd") && temp.contains("-even")) {
                System.out.println("\nThe request contains mutually exclusive properties: [ODD, EVEN]\n" +
                        "There are no numbers with these properties.");
                return false;
            } else if (temp.contains("square") && temp.contains("sunny") ||
                    temp.contains("-square") && temp.contains("-sunny")) {
                System.out.println("\nThe request contains mutually exclusive properties: [SQUARE, SUNNY]\n" +
                        "There are no numbers with these properties.");
                return false;
            } else if (temp.contains("spy") && temp.contains("duck") ||
                    temp.contains("-spy") && temp.contains("-duck")) {
                System.out.println("\nThe request contains mutually exclusive properties: [SPY, DUCK]\n" +
                        "There are no numbers with these properties.");
                return false;
            } else if (temp.contains("happy") && temp.contains("sad") ||
                    temp.contains("-happy") && temp.contains("-sad") ) {
                System.out.println("\nThe request contains mutually exclusive properties: [HAPPY, SAD]\n" +
                        "There are no numbers with these properties.");
            }

            threePlus = new HashMap<>(input.length - 2);
            for (int i = 0; i < input.length - 2; i++) {
                String tmp = input[i + 2];
                if (tmp.startsWith("-")) {
                    tmp = tmp.substring(1);
                    if (temp.contains(tmp)) {
                        System.out.printf("The request contains mutually exclusive properties: [%s, -%s]\n" +
                                "There are no numbers with these properties.", tmp, tmp);
                        return false;
                    }
                    threePlus.put(tmp.toLowerCase(), false) ;
                } else {
                    threePlus.put(tmp.toLowerCase(), true) ;
                }
            }
            return isOneValid(input[0]) && isTwoValid(input[1]) &&
                    isThreePlusValid(threePlus.keySet());
        }
        return false;
    }

    static boolean isOneValid(String input) {
        try {
            one = Long.parseLong(input);
            if (one < 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("\nThe first parameter should be a natural number or zero.");
            return false;
        }
    }

    static boolean isTwoValid(String input) {
        try {
            two = Long.parseLong(input);
            if (two < 1) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("\nThe second parameter should be a natural number.");
            return false;
        }
    }

    static boolean isThreePlusValid(Set<String> input) {
        List<String> temp = Arrays.asList(
                "even", "odd", "buzz", "duck", "palindromic", "gapful",
                "spy", "square", "sunny", "jumping", "happy", "sad");
        List<String> wrongs = input.stream()
            .filter(prop -> !temp.contains(prop))
            .collect(Collectors.toList());

        if (wrongs.size() == 1) {
            System.out.printf("The property %s is wrong.\n" +
                    "Available properties: " +
                    "[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]", wrongs.get(0));
        } else if (wrongs.size() > 1){
            System.out.printf("The properties %s are wrong.\n" +
                    "Available properties: " +
                    "[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]", wrongs);
        }
        return wrongs.size() == 0;
    }
}