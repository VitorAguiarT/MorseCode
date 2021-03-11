import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Random;
import java.util.StringJoiner;

public class TestCaseGenerator{
    static Random rnd = new Random();
    static Set<String> codes = new HashSet<>();
    String giantCode = generateCode((int) 1e6);

    public static void main(String[] args) {
        int nCommands = rnd.nextInt(Integer.parseInt(args[0]));
        StringJoiner test = new StringJoiner("\n");
        for(int i = 0; i < nCommands; i++){
            test.add(generateCommand());
        }
        //System.out.print(test.toString());
        System.out.println(specialCase());
    }

    public static String specialCase(){
        StringJoiner test = new StringJoiner("\n");
        for(int i = 0; i < (int) 1e2; i++){
            test.add(generateInsert((int)1e4));
        }
        for(int i = 0; i < 1e2; i++){
            test.add(generateSearch((int) 1e4));
        }
        return test.toString();
    }

    public static String generateCode(int size){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < size; i++){
            str.append(rnd.nextInt(2) == 0 ? '.' : '-');
        }
        return str.toString();
    }

    public static String generateCommand(){
        int type = rnd.nextInt(4);
        switch(type){
            case(0):
                return generateInsert((int) 1e2);
            case(1):
                return generateRemove();
            case(2):
                return generateSearch((int) 1e4);
            default:
                return generateDecode(5);
        }
    }

    private static String generateInsert(int size){
        StringBuilder insert = new StringBuilder("INSERIR: " + (char)('A' + rnd.nextInt('z'-'A')) + " ");
        String code;
        if(rnd.nextInt(2) == 0){
            code = getRandomExistingCode();
        }else{
            code = generateCode((int) size);
        }
        codes.add(code);
        insert.append(code);
        return insert.toString();
    }

    private static String generateRemove(){
        StringBuilder insert = new StringBuilder("REMOVER: ");
        String code;
        if(rnd.nextInt(2) == 0){
            code = getRandomExistingCode();
        }else{
            code = generateCode((int) 1e2);
        }
        codes.remove(code);
        insert.append(code);
        return insert.toString();
    }

    private static String generateSearch(int size){
        StringBuilder search = new StringBuilder("BUSCAR: ");
        if(rnd.nextInt(2) == 0){
            search.append(getRandomExistingCode());
        }else{
            search.append(generateCode(size));
        }
        return search.toString();
    }

    private static String generateDecode(int size){
        StringBuilder str = new StringBuilder("DECODIFICAR:");
        if(rnd.nextInt(2) == 0){
            for(int i = 0; i<size; i++){
                str.append(" " + getRandomExistingCode());
            }
        }else{
            for(int i = 0; i<size; i++){
                if(rnd.nextInt(2) == 0)
                    str.append(" " + getRandomExistingCode());
                else
                    str.append(" " + generateCode(10));
            }
        }
        return str.toString();
    }

    private static String getRandomExistingCode(){
        if(codes.size() == 0){
            return generateCode(10);
        }
        int i = rnd.nextInt(codes.size());
        Iterator<String> it = codes.iterator();
        for(int j=0; j<i; j++){
            it.next();
        }
        return it.next();
    }
}