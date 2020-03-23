import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class SaveFileTest {

    @Test
    public void saveDialog(){
        String hello = "";
        String filename = "src/test/resources/hello.txt";

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "utf-8"))) {
            writer.write("hello");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)))){
            hello = reader.readLine();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        assertEquals("hello", hello);
    }
}
