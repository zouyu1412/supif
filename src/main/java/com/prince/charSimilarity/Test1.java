package com.prince.charSimilarity;

import java.text.Collator;
import java.util.*;

public class Test1 {


    private int compare(String str, String target) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1

        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取两字符串的相似度
     *
     * @param str
     * @param target
     * @return
     */
    public double getSimilarityRatio(String str, String target) {
        str = str.replace("公司","").replace("事业","").replace("部","").replace("项目","").replace("工程","");
        target = target.replace("公司","").replace("事业","").replace("部","").replace("项目","").replace("工程","");
        return 1 - (double) compare(str, target) / Math.max(str.length(), target.length());
    }

    public static void main(String[] args) {
        Test1 t = new Test1();

        String tag = "机电";//正确的
        String test1 = "广东";
        String test2 = "机电安装工程";
        System.out.println("similarityRatio=" + t.getSimilarityRatio(test1, tag));
        System.out.println("similarityRatio=" + t.getSimilarityRatio(test2, tag));

        List<String> gepsOrgs = new ArrayList<>();
        List<String> cloudtOrgs = new ArrayList<>();

        gepsOrgs.add("商丘市城乡一体化示范区平安棚户区改造工程项目部");
        gepsOrgs.add("商丘市城乡一体化示范区和悦棚户区改造工程");
        gepsOrgs.add("商丘市高铁建设区棚户区改造一期工程");
//        gepsOrgs.add("商丘市城乡一体化示范区董庄棚户区改造工程");
//        gepsOrgs.add("商丘医学高等专科学校新校区");
        gepsOrgs.sort((o1,o2)->Collator.getInstance(Locale.CHINESE).compare(o1,o2));
        System.out.println(gepsOrgs);

        cloudtOrgs.add("商丘高铁建设区棚户区改造一期安置房工程");
        cloudtOrgs.add("商丘城乡一体化示范区和悦棚户区改造工程");
        cloudtOrgs.add("商丘高铁建设区棚户区改造一期安置房项目");
        cloudtOrgs.add("商丘市城乡一体化示范区和悦棚户区改造工程项目部");
        cloudtOrgs.add("商丘市城乡一体化示范区平安社区棚户改造工程");
//        cloudtOrgs.add("商丘城乡一体化示范区董庄棚户区改造工程");
//        cloudtOrgs.add("商丘医学高等专科学校新校区");
        cloudtOrgs.sort((o1,o2)->Collator.getInstance(Locale.CHINESE).compare(o1,o2));
        System.out.println(cloudtOrgs);

        Map<String, String> stringStringMap = t.handleOrgRs(gepsOrgs, cloudtOrgs);

        System.out.println("==============================");
        stringStringMap.forEach((k,v)->System.out.println("key:"+k+"  value:"+v));
        System.out.println("==============================");
        gepsOrgs.forEach(System.out::println);
        System.out.println("===============================");
        cloudtOrgs.forEach(System.out::println);
        System.out.println("===============================");
    }

    private Map<String,String> handleOrgRs(List<String> gepsOrgs, List<String> cloudtOrgs) {
        Collections.sort(gepsOrgs);
        Collections.sort(cloudtOrgs);
        Map<String,String> res = new HashMap<>();
        for(Iterator<String> it = gepsOrgs.iterator();it.hasNext();){
            String gepsOrg = it.next();
            for(Iterator<String> iter = cloudtOrgs.iterator();iter.hasNext();){
                String cloudtOrg = iter.next();
                if(gepsOrg.equals(cloudtOrg)){
                    res.put(gepsOrg,cloudtOrg);
                    it.remove();
                    iter.remove();
                    break;
                }
            }
        }
        for(String geps:gepsOrgs){
            String bestMatch = "";
            double maxRatio = 0.5;
            if(cloudtOrgs.isEmpty()){
                break;
            }
            for(String cloudt:cloudtOrgs){
                double temRatio = getSimilarityRatio(geps,cloudt);
                if(temRatio >= maxRatio){
                    maxRatio = temRatio;
                    bestMatch = cloudt;
                }
            }
            if(!"".equals(bestMatch)){ //匹配成功
                boolean canMatch = true;
                for(String gepsss:gepsOrgs){   //逆向验证是否是最佳匹配
                    if(!gepsss.equals(geps) && !res.containsKey(gepsss) && getSimilarityRatio(gepsss,bestMatch) > maxRatio){
                        canMatch = false;
                    }
                }
                if(canMatch) {
                    res.put(geps,bestMatch);
                    cloudtOrgs.remove(bestMatch);
                }
            }
        }
        return res;
    }
}