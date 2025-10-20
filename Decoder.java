public class Decoder {
  private static String map = "ABCDEFGHIJKLMNOPQRSSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890+/";

  private static String getNormalFromBase64(String str) {
    StringBuilder sb = new StringBuilder();

    int len = str.length();

    for(int i = 0; i < len; i++) {
      int base64AsciiOne = map.indexOf(str.charAt(i));
      int base64AsciiTwo = map.indexOf(str.charAt(i + 1));
      int base64AsciiThree = str.charAt(i + 2) == '=' ? -1 : map.indexOf(str.charAt(i + 2));
      int base64AsciiFour = str.charAt(i + 3) == '=' ? -1 : map.indexOf(str.charAt(i + 3));

      int n = 0;

      n |= (base64AsciiOne << 18);
      n |= (base64AsciiTwo << 12);
      n |= (base64AsciiThree == -1 ? 0 : (base64AsciiThree << 6));
      n |= (base64AsciiFour == -1 ? 0 : base64AsciiFour);

      // extract the original bytes

      sb.append((char) ((n >> 16) & 255));

      if(base64AsciiThree != -1) sb.append((char) (n >> 8) & 255);
      if(base64AsciiFour != -1) sb.append((char) n & 255);

    }

    return sb.toString();
  }

  public static void main(String[] args) {
    String str = "abcd";
    String base64 = getNormalFromBase64(str);

    System.out.println(str + " in base64 form is " base64);
  }

}
