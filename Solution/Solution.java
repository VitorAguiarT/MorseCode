import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        long initial = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        Tree tree = new Tree();
        while(in.hasNextLine()){
            String[] command = in.nextLine().split(": ");
            if(command[0].equals("FIM")){
                break;
            }else if(command[0].equals("INSERIR")){
                char c = command[1].charAt(0);
                String code = command[1].split(" ")[1];
                if(tree.Insert(c, code)){
                    System.out.println("Caractere inserido com sucesso");
                }else{
                    System.out.println("Codigo ja utilizado");
                }
            }else if(command[0].equals("REMOVER")){
                String code = command[1];
                if(tree.remove(code)){
                    System.out.println("Caractere removido com sucesso");
                }else{
                    System.out.println("Codigo Invalido");
                }
            }else if(command[0].equals("BUSCAR")){
                String code = command[1];
                Character c = tree.search(code);
                if(c != null){
                    System.out.println(c);
                }else{
                    System.out.println("Codigo Invalido");
                }
            }else if(command[0].equals("DECODIFICAR")){
                String codes = command[1];
                System.out.println(tree.decode(codes));
            }
        }
        System.err.println((System.currentTimeMillis() - initial) + " ms");
    }
}

class Tree{
    TreeNode root;

    public Tree(){
        root = new TreeNode();
    }

    public boolean Insert(Character c, String code){
        TreeNode aux = root;
        for(char ch : code.toCharArray()){
            if(ch == '.'){
                if(aux.left == null){
                    aux.left = new TreeNode();
                } 
                aux = aux.left;
            }else if(ch == '-'){
                if(aux.right == null){
                    aux.right = new TreeNode();
                } 
                aux = aux.right;
            }else{
                throw new IllegalArgumentException("Invalid code");
            }
        }
        if(aux.c != null) return false;
        aux.c = c;
        return true;
    }

    public Character search(String code){
        TreeNode aux = root;
        for(char c : code.toCharArray()){
            if(c == '.'){
                if(aux.left == null) return null;
                aux = aux.left;
            }else if(c == '-'){
                if(aux.right == null) return null;
                aux = aux.right;
            }else return null;
        }
        return aux.c;
    }

    public String decode(String codes){
        StringBuilder str = new StringBuilder();
        for(String code : codes.split(" ")){
            Character c = this.search(code);
            if(c == null) return String.format("Codigo Invalido: %s", code);
            str.append(c);
        }
        return str.toString();
    }

    public boolean remove(String code){
        return remove(root, code, 0);
    }

    private boolean remove(TreeNode node, String code, int i){
        if(node == null) return false;
        boolean removed = false;
        if(i < code.length()){
            if(code.charAt(i) == '.'){
                removed = remove(node.left, code, i+1);
                if(removed && removable(node.left)){
                    node.left = null;
                }
            }else if(code.charAt(i) == '-'){
                removed = remove(node.right, code, i+1);
                if(removed && removable(node.right)){
                    node.right = null;
                }
            }else{
                return false;
            }
        }else{
            if(node.c == null) return false;
            node.c = null;
            return true;
        }
        return removed;
    }

    private boolean removable(TreeNode node){
        return node.c == null && node.left == null && node.right == null;
    }

}

class TreeNode{
    Character c;
    TreeNode left;
    TreeNode right;

    public TreeNode(){}

    public TreeNode(Character c){
        this.c = c;
    }
}