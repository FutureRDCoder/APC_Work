/**
 * This class contains methods to convert a normal string of UNICODE to Base64
 * and from back from Base64 to normal string. 
 */
public class Base64 {

  private static String map = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");

  private static String getNormalFromBase64(String str) {

    StringBuilder ans = new StringBuilder();
    
    int len = str.length();
    for (int i = 0; i < len; i+=4) {
      int base64AsciiOne = map.indexOf(str.charAt(i)); 
      int base64AsciiTwo = map.indexOf(str.charAt(i + 1)); 
      int base64AsciiThree = str.charAt(i + 2) == '=' ? 0 : map.indexOf(str.charAt(i + 2)); 
      int base64AsciiFour = str.charAt(i + 3) == '=' ? 0 : map.indexOf(str.charAt(i + 3)); 

      int n = 0;

      n |= base64AsciiOne << 18;
      n |= base64AsciiTwo << 12;
      n |= base64AsciiThree << 6;
      n |= base64AsciiFour;
      
      ans.append((char) ((n >> 16) & 255));
      ans.append((char) ((n >> 8) & 255));
      ans.append((char) ((n) & 255));

    }
    return ans.toString();
  }

  private static String getBase64FromNormal(String str) {

    int len = str.length();
    StringBuilder ans = new StringBuilder();

    for (int i = 0; i < len; i += 3) {
      if (len - i == 1) { // // if we are left with 1 character1 then handle differently
        int n = 0;
        n |= (int) (str.charAt(i) << 4); // pad the zeroes

        ans.append(map.charAt((n >> 6) & 63));
        ans.append(map.charAt((n) & 63));
        ans.append('=');
        ans.append('=');
      } else if (len - i == 2) { // if we are left with 2 characters then handle differently
        int n = 0;
        n |= (int) (str.charAt(i) << 10);
        n |= (int) (str.charAt(i + 1) << 2);

        ans.append(map.charAt((n >> 12) & 63));
        ans.append(map.charAt((n >> 6) & 63));
        ans.append(map.charAt((n & 63)));
        ans.append('=');
      } else { // take triplet
        int n = 0; // number to store the 3 octets
        n |= (int) (str.charAt(i) << 16);
        n |= (int) (str.charAt(i + 1) << 8);
        n |= (int) (str.charAt(i + 2));

        // now take 4 sextets, right shift the number to extract the required sextet.
        // & with 111111 to get the valid ones
        ans.append(map.charAt((n >> 18) & 63));
        ans.append(map.charAt((n >> 12) & 63));
        ans.append(map.charAt((n >> 6) & 63));
        ans.append(map.charAt((n & 63)));
      }
    }
    return ans.toString();
  }
  
  /**
   * The entry point.
   * @param args
   */
  public static void main(String[] args) {
    String str = "abcd";
    String base64 = getBase64FromNormal(str);

    System.out.println(str + " in Base64 form: " + base64);
    System.out.println(base64 + " in normal form: " + getNormalFromBase64(base64));
  }
}
