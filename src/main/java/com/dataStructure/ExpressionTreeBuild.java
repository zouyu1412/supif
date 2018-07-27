package com.dataStructure;

public class ExpressionTreeBuild {
    public static void main(String[] args) {
//  String[] expression=new String[]{"2", "*" ,"6" ,"-" ,"(", "23", "+", "7", ")", "/", "(", "1" ,"+" ,"2", ")"};  
//  System.out.println(build(expression));  
        String[] expression = new String[]{"2", "*", "6", "-", "(", "23", "+", "7", ")", "/", "(", "1", "+", "2", ")"};
        System.out.println(build(expression));
    }

    /**
     * @param expression: A string array
     * @return: The root of expression tree
     */
    public static ExpressionTreeNode build(String[] expression) {
        // write your code here  
        int[] prio = new int[expression.length];
        int valiLen = dealPrio(expression, prio);//处理后有效数据的长度
        ExpressionTreeNode treeNode = buildFle(expression, prio, 0, valiLen - 1);
        return treeNode;

    }

    /**
     * @param expression
     * @param prio       符号优先级处理
     */
    public static int dealPrio(String[] expression, int[] prio) {
        int kuohao = 0;//用于括号里面操作符号升级
        for (int i = 0; i < expression.length; i++) {
            switch (expression[i]) {
                case "-":
                    prio[i] = 1 + kuohao;
                    break;
                case "+":
                    prio[i] = 1 + kuohao;
                    break;
                case "*":
                    prio[i] = 2 + kuohao;
                    break;
                case "/":
                    prio[i] = 2 + kuohao;
                    break;
                case "(":
                    kuohao += 2;
                    prio[i] = 32767;
                    break;
                case ")":
                    kuohao -= 2;
                    prio[i] = 32767;
                    break;
                default:
                    prio[i] = -32768;
            }
        }
        int j = 0;//对括号进行去除操作
        for (int i = 0; i < expression.length; i++) {
            if (expression[i] == "(" || expression[i] == ")") {
                continue;
            } else {
                expression[j] = expression[i];
                prio[j] = prio[i];
                j++;
            }
        }
        return j;//有小数据的长度  
    }

    public static ExpressionTreeNode buildFle(String[] expression, int[] prio, int start, int end) {
        if (start == end)//此时只有有个值的点
        {
            ExpressionTreeNode treeNode = new ExpressionTreeNode(expression[start]);
            return treeNode;
        } else {
            //如果不只是有值  
            int prioOpe = Integer.MAX_VALUE;//记录优先级最低的操作符值
            int currentnum = start;//记录最低的优先级操作符位置
            //查找最小级别的操作符  
            for (int i = start; i <= end; i++) {
                if (prio[i] != -32768 && prio[i] < prioOpe) {
                    prioOpe = prio[i];
                    currentnum = i;
                }
            }
            //当找到最小的操作符后，以此操作符为根做扩展  
            ExpressionTreeNode expressionTreeNode = new ExpressionTreeNode(expression[currentnum]);
            expressionTreeNode.left = buildFle(expression, prio, start, currentnum - 1);
            expressionTreeNode.right = buildFle(expression, prio, currentnum + 1, end);
            return expressionTreeNode;
        }
    }
}

class ExpressionTreeNode {
    public String symbol;
    public ExpressionTreeNode left, right;

    public ExpressionTreeNode(String symbol) {
        this.symbol = symbol;
        this.left = this.right = null;
    }
}