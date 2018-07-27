package com.ebimatch;

import java.text.Collator;
import java.util.*;

/**
 * Created by zouy-c on 2018/7/5.
 */
public class AlUtil {

    public static void main(String[] args) {

//        BiOrgDTO master = new BiOrgDTO();
//        master.setId(11L);
//        master.setName("广联达");
//        List<BiOrgDTO> masSub = new ArrayList<>();
//        BiOrgDTO m1= new BiOrgDTO();
//        m1.setId(22L);
//        m1.setName("造价bg");
//        BiOrgDTO m2= new BiOrgDTO();
//        m2.setId(33L);
//        m2.setName("施工bg");
//        masSub.add(m1);
//        masSub.add(m2);
//        master.setChildNodes(masSub);
//
//        BiOrgDTO slave = new BiOrgDTO();
//        slave.setId(1111L);
//        slave.setName("广联达软件");
//        List<BiOrgDTO> slaSub = new ArrayList<>();
//        BiOrgDTO s1= new BiOrgDTO();
//        s1.setId(2222L);
//        s1.setName("造价部");
//        BiOrgDTO s2= new BiOrgDTO();
//        s2.setId(2233L);
//        s2.setName("施工bg");
//        slaSub.add(s1);
//        slaSub.add(s2);
//        slave.setChildNodes(slaSub);

        AlUtil al = new AlUtil();
//        Map<Long,Long> map = al.match(master,slave);

//        System.out.println(map);

        System.out.println(al.compare("uuuuu","zouuuuuuyu"));
    }

    //匹配生成masterOrg id到slaveOrg id 的关系映射
    public Map<Long,Long> match(BiOrgDTO masterOrg, BiOrgDTO slaveOrg){
        if(masterOrg == null || slaveOrg == null){
            return Collections.emptyMap();
        }
        //映射集
        Map<Long,Long> resultMap = new HashMap<>();
        //添加集团映射
        resultMap.put(masterOrg.getId(),slaveOrg.getId());

        recursion(masterOrg,slaveOrg,resultMap);

        return resultMap;

    }

    private void recursion(BiOrgDTO masterOrg, BiOrgDTO slaveOrg, Map<Long,Long> orgIdMap) {

        List<BiOrgDTO> masterOrgs = masterOrg.getChildNodes();
        List<BiOrgDTO> slaveOrgs = slaveOrg.getChildNodes();
        if(null == masterOrgs || masterOrgs.isEmpty() || null == slaveOrgs || slaveOrgs.isEmpty()){
            return;
        }

        //构建组织最佳匹配映射
        Map<BiOrgDTO,BiOrgDTO> map = handleOrgRs(masterOrgs,slaveOrgs);

        for(Map.Entry<BiOrgDTO,BiOrgDTO> entry:map.entrySet()){
            BiOrgDTO key = entry.getKey();
            BiOrgDTO value = entry.getValue();
            orgIdMap.put(key.getId(),value.getId());
            recursion(key,value,orgIdMap);
        }
    }

    /**
     * 构建组织映射关系
     * @param masterOrgs
     * @param slaveOrgs
     * @return
     */
    private Map<BiOrgDTO,BiOrgDTO> handleOrgRs(List<BiOrgDTO> masterOrgs, List<BiOrgDTO> slaveOrgs) {
        //排序
        if(masterOrgs == null || slaveOrgs == null){
            System.out.println("dsadasdasd");
        }else{
            System.out.println(masterOrgs.stream().filter(i->i.getName()==null).count());
        }
        masterOrgs.sort(((o1, o2) -> Collator.getInstance(Locale.CHINESE).compare(o1.getName(),o2.getName())));
        slaveOrgs.sort(((o1, o2) -> Collator.getInstance(Locale.CHINESE).compare(o1.getName(),o2.getName())));
        Map<BiOrgDTO,BiOrgDTO> res = new HashMap<>();

        //完全匹配
        for(Iterator<BiOrgDTO> it = masterOrgs.iterator();it.hasNext();){
            BiOrgDTO masOrg = it.next();
            if(slaveOrgs.isEmpty()) break;
            for(Iterator<BiOrgDTO> iter = slaveOrgs.iterator();iter.hasNext();){
                BiOrgDTO slaOrg = iter.next();
                if(masOrg.getName().equals(slaOrg.getName())){
                    res.put(masOrg,slaOrg);
                    it.remove();
                    iter.remove();
                    break;
                }
            }
        }

        //模糊匹配
        for(BiOrgDTO mas:masterOrgs){
            BiOrgDTO bestMatch = null;
            //匹配阈值
            double maxRatio = 0.5;
            if(slaveOrgs.isEmpty()){
                break;
            }
            for(BiOrgDTO sla:slaveOrgs){
                double temRatio = getSimilarityRatio(mas.getName(),sla.getName());
                if(temRatio >= maxRatio){
                    maxRatio = temRatio;
                    bestMatch = sla;
                }
            }
            if(null != bestMatch){ //匹配成功
                boolean canMatch = true;
                for(BiOrgDTO newMas:masterOrgs){   //逆向验证是否是最佳匹配
                    if(!newMas.equals(mas) && !res.containsKey(newMas.getId()) && getSimilarityRatio(newMas.getName(),bestMatch.getName()) > maxRatio){
                        canMatch = false;
                    }
                }
                if(canMatch) {
                    res.put(mas,bestMatch);
                    slaveOrgs.remove(bestMatch);
                }
            }
        }
        return res;
    }

    /**
     * 获取两字符串的相似度，去掉常见关键字
     *
     * @param str
     * @param target
     * @return
     */
    public double getSimilarityRatio(String str, String target) {
//        str = str.replace("公司","").replace("事业","").replace("部","").replace("项目","").replace("工程","");
//        target = target.replace("公司","").replace("事业","").replace("部","").replace("项目","").replace("工程","");
        return 1 - (double) compare(str, target) / Math.max(str.length(), target.length());
    }

    private int compare(String str, String target) {
        int n = str.length();
        int m = target.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        char ch1;
        char ch2;
        int temp;
        int[][] d = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (int i = 1; i <= n; i++) {
            ch1 = str.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }

        for(int i=0;i<=n;i++){
            System.out.println(Arrays.toString(d[i]));
        }
        return d[n][m];
    }

    private int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

}
