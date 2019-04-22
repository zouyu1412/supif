package com.dataStructure.searchtree;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class SearchUtils {

    private static final Logger logger = LoggerFactory.getLogger(SearchUtils.class);

    public static SearchTree searchTree = new SearchTree();

    public static final String hotWordUrl = "http://10.16.57.99:81/hotwordlib_json.jsp";



    public static void main(String[] args){

//        String res = HttpUtil.doGet(hotWordUrl);
        String res = "";
        JSONObject jsonObject = JSONObject.parseObject(res);
        Set<String> keys = jsonObject.keySet();
        int count = 0;
        int total = 0;
        for(String key:keys) {
            total++;
            String value = jsonObject.getString(key);
            if(value.startsWith("http://dealer")){
                count++;
            }
            searchTree.insert(key,value);
            logger.info("success insert key: {}",key);
        }
        logger.info("total:"+total+" count:"+count);
        long s = System.currentTimeMillis();
        String content1 = "是河北通菱汽车销售服务有限公司石家庄分公司sfdsfd哈哈amg gt黄河科技哈看得见美通荣威汽车还是sgsfg啥的建行卡号健康和健康好德国宝沃济宁骥源4S店房价奇偶简单来说卡据了解借款分类科名爵TF技地方迈锐宝混合动力";
        String content = "<p><strong>【搜狐汽车 车先知】</strong>每个人对“槽点”的理解不一样，挑剔的人会认为座椅角度、细节设计等处处不合心意，但大多人还是能做到放平心态，用正确的眼光去看待一款整体评分80分以上的产品。所以如果针对细枝末节吐槽，那今天的话题恐怕永无止境；但话说回来，在中国混得风生水起，口碑市场双丰收的“BBA”难道会有“死角”？这个嘛，我说您听，赞同或者不赞同都欢迎评论区开聊（喷）。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/14a46bdee3014dadbac4d041c6f3a8ab.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/25321c95b3bb42c59d7734917bd25a3d.jpeg\" max-width=\"600\"></p> \n" +
                "<h1>奥迪篇</h1> \n" +
                "<p><strong>· 不用给我们引进A4 Avant了，咱不稀罕！</strong></p> \n" +
                "<p>这话题是第一天说了么？从B9这代A4国产引入，就有消息说A4 Avant会同步引进，这是好消息啊，当下年轻消费者很追求与众不同，金钱上的代价不会比国产A4L高多少就能得到原汁原味的旅行版，奥迪真是吃透了中国市场！可惜承诺并未兑现，2016拖到2017，2017说2018四季度稳了，结果2018还没引进，现在据可靠消息，奥迪会在2019年把A4 Avant引入国内，要我说的话，动作快点吧，海外都要中期改款了，国内还没尝鲜呢...</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/57bf6c3c6e1a4cbaaf76afe4318f4a2e.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/72853af3d46f4d4791b293690da41c9e.jpeg\" max-width=\"600\"></p> \n" +
                "<p>说起来国内有A4 allroad quattro，而且2019款上市以后增配幅度很大，这个举动笔者无论如何是要点赞的。虽然看上去都是旅行版，但无疑“Avant”会呼声更高一些，allroad系列“奶爸”味儿还是过于浓了。而且我想不出引进A4 Avant对于奥迪有啥难点，现在国内一线城市对豪华品牌进口车型需求量是越来越大的，单从“商机”这个角度，A4 Avant也会比A4 allroad quattro销量更高。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/9663f6be913947e69f307837656d5a45.jpeg\" max-width=\"600\"></p> \n" +
                "<p><strong>· 配置奇葩的2019款奥迪A5家族</strong></p> \n" +
                "<p>现款A5家族包含敞篷版，双门版以及四门版，从销量上，四门版也就是A5 Sportback相对多一些，也相对保值。按道理这车就算有槽点，也被高颜值覆盖了，一切为颜值买单！可是奥迪A5谜一般的动力排布与配置排布还是让人啼笑皆非，与A4L相同，A5在国内也应用2.0T动力并分高低功率调教，不同之处在于高功率发动机并不标配四驱，其四驱门槛非常高，无论四门双门敞篷，想要四驱上顶配就对了；再换句话说，A5家族大部分车型都是前驱版，低功率发动机配前驱就足够充沛了，高功率配前驱会有一些动力过剩。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/29788cdcee514704bf792307c1bd28d4.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/3abff34757ef4097a372542796eb0042.jpeg\" max-width=\"600\"></p> \n" +
                "<p>国内消费者也很识趣，买A5的话，要么图颜值买最低配，有钱又想要机械素质就买四驱，中间的45 TFSI前驱车型其实定位有些尴尬。都说存在即道理，车款如何排布也不影响消费者切身利益；但奥迪对A5的增配才是高潮戏，既没照顾最具购买潜力的低配，也没照顾四驱版，反而定位尴尬的45 TFSI前驱版是最大赢家，看不懂啊...于是那些为颜值买单的低配车主继续被嘲讽：“你看，落地花四十几万买个车连电折耳都没有...哈哈哈...”</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/349ddf5995c34678816749182d4e1782.jpeg\" max-width=\"600\"></p> \n" +
                "<p><strong>· 致现款A6L--老将的退役仪式本该体面</strong></p> \n" +
                "<p>根据奥迪新车规划，2018年末本该有一次C7 &amp; C7Pa世代A6L的小改款，或者说叫A6L典藏版也好，加一些配置，再高调上市一次。事实上这款车确实在规划中，但奥迪至今还未“官宣”什么，目前到达经销商的现款A6L都是典藏版，低配加入了运动套件，2.0T增配全景天窗，经典的3.0T六缸机械增压版本则已经停产了。其实也说不上是吐槽，但会觉得眼下现款A6L相关动作都是暗箱操作，全世界都在嚷嚷全新A6科技感如何如何，至于“老”A6嘛，有点人走茶凉。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/aeefc3a87c9942ada0573fd6acb21ee9.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/1e90303c78af46ce89cb1c9ee8d48abf.jpeg\" max-width=\"600\"></p> \n" +
                "<p>据笔者从经销商处了解，现款A6L与全新A6L同堂销售周期会非常短，大批量供货也就是截止到春节前后。目前，全新A6L已经开始全面铺货，接受预订并将于1月15日公布价格。虽说新老交替是常事，但个人认为现款A6L作为有功之臣，退出历史舞台时应该更体面一些，总不至于像现在这样悄无声息；而且说句题外话，C7世代的A6L确实在制造理念上已经很旧了，相关平台早就革新了，但作为豪华车，新老A6L相较下，老款车型还是有自己的魅力点，之所以那么多人认为老款不值得购买，无非是不同年龄段的人在驾驶感受以及内外设计两方面，对“豪华”一词的理解有代沟罢了。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/57fc8463f85946969eae509cc1ea3c99.jpeg\" max-width=\"600\"></p> \n" +
                "<h1>宝马篇</h1> \n" +
                "<p><strong>· 3系最后一次小改款，有惊喜也有失落</strong></p> \n" +
                "<p>宝马这代车型实力很强，说真的可吐槽之处不多，甚至很多历史遗留问题在2018都顺利解决。但如今的宝马依然很“务实”，没什么情怀可聊，“你们不用买高功率，低功率就够你们用了，我们调教好，况且高功率门槛太高了你们买不起”，差不多就是这种感觉。在配置差不多的前提下，320im曜夜版比330im曜夜落地价便宜近10万，纯粹的驾控乐趣代价可是真不低；但现在好了，宝马在2019款3系上取消了“330”系列，有钱都买不到了。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/11326dfa5fe64681a9eb161c1b422251.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/f0f343a472504266a6f7407bacdbcd23.jpeg\" max-width=\"600\"></p> \n" +
                "<p>对新款（2019款）3系的吐槽，可能纯属个人好恶吧，这样出色的机械素质+这种程度的诚意配置，现在入手这代3系已是最佳时机。尤其从2018款开始推出的“曜夜版”性价比爆棚，唯一槽点是标轴曜夜一律装配熏黑轮毂值得商榷，哪怕熏黑/枪灰色轮毂配额比例为一比一都好。个人认为，所谓熏黑效果对轮毂造型以及车漆颜色比较挑剔，如果是20寸405M轮毂熏黑配上3系墨尔本红或埃斯托蓝车漆，效果会更好一些。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/b70b82318be54f57b8b50512d7d4060c.jpeg\" max-width=\"600\"></p> \n" +
                "<p><strong>· 处境尴尬的宝马纯电动车</strong></p> \n" +
                "<p>以前我们不懂为何有人要消费纯电动汽车，但现在形势已经很明朗，在北京（或上海），取得一辆新能源汽车牌照要比燃油车容易得多，从前只被看作是有钱人玩具的宝马i3，如今也进入了更多消费者视野。但这几年，确切地说是从2015年到2018年底，国内销售的i3差强人意，电池容量从19千瓦时涨到33千瓦时，但实测续航里程与中国品牌相比尚不能平分秋色，好在近期落地价格很实惠，三十几万入手一台宝马品牌进口电动汽车也算值回票价。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/9097e852514a4576b3b9fa4f80fa4596.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/a2df96706638400ca17698ba188c5d03.jpeg\" max-width=\"600\"></p> \n" +
                "<p>作为帝都排队等待购入新能源车的一员，期间也陆续接触了一批纯电动汽车，可能大部分买“电动爹”的车主是为了代步，但代步这事儿也分三六九等，对于手头足够宽裕只是碍于购车指标受限的买主，可能更希望追求合资甚至进口品牌，遗憾的是目前合资或进口领域可选产品并不丰富，30万-50万区间更是一片空白。据说，续航更出色的新款i3即将投产，可惜如果我是个有钱人（我作为一个有钱人），八成会更期待奥迪e-tron，贵是贵了点，产品力可是吊打。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/29ddebd45cd441f6aa4e56e42d5b362c.jpeg\" max-width=\"600\"></p> \n" +
                "<h1>奔驰篇</h1> \n" +
                "<p><strong>· 我是个太监，但今天还就操皇帝的心了</strong></p> \n" +
                "<p>如果不出意外，这辈子是买不起CLS了，说起来也是曾经梦想拥有的座驾，但我的梦之车绝对不是现如今在售这款。美丑这事儿没有准则，但外界对第三代CLS外形的吐槽令人不能忽视，第一代CLS四门轿车造型流畅，第二代CLS猎装版逼格爆棚，但最新这一代无论怎么看都有点“怪”...对比三代CLS，最新一代CLS风挡车窗占比最大，驾驶舱最靠前，车身视觉重心最靠前，其实它不用太照顾后排，帅，永远是CLS第一生产力。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/87a208ef73c84b9eadbbf2650ce8451d.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/84ad60f522024ffb945eeaccee9a7126.jpeg\" max-width=\"600\"></p> \n" +
                "<p>如果类似车型仅此一款，那这波吐槽实在没什么价值，可惜隔壁全新A7 Sportback太强势了，售价差不多，动力水平更出色，各方面配置更出色，内饰逼格与CLS打成平手问题不大，至于外观嘛，基本就是按在地上摩擦了。难受的是奥迪A7实用性也不差，而且奔驰并非只此一版设计方案，它们拿得出手的设计有很多，可在最需要颜值的一款车型上效果差强人意。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/811f4cf711ba4d7989b682264d277c2d.jpeg\" max-width=\"600\"></p> \n" +
                "<p><strong>· 对48V混动系统的“蜜汁”信任</strong></p> \n" +
                "<p>9月，奔驰申报了E 260 L动力信息，1.5T发动机+48V微混系统将取代现款E 200 L，这种事儿发生在C级上还能理解，但奔驰E级强调的是豪华，是行政，考虑到低配车型（现款E 200 L，未来的E 260 L）市场需求量很大，这种调整是否具备说服力？当然现在还不能下定论，E级上应用的1.5T发动机驾驶感受（包括高级感、平顺性与绝对动力表现）就一定比2.0T差，但在此前C级改款时确实被证明是这样的。</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/43fd09598cb548629c8ed2c89eb6f42a.jpeg\" max-width=\"600\"></p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/8f38107587dc4cecb59c97c547284ba7.jpeg\" max-width=\"600\"></p> \n" +
                "<p>为什么要吐槽这点？纵观2018，奥迪对动力系统态度很是开明，第三代EA888业内口碑不差，配上全时四驱独树一帜，“傻快”那也是快啊！重要的是这套实力不俗的动力系统代价不高，国产A4L个性运动版裸车价格基本只在27-29万左右。宝马呢，稍微不近人情一点，有钱买6缸或者买B48高功率定是不错，没钱呢，买B48低功率也不比谁差。奔驰这边，低配车型全面1.5T就真的不够意思了，当然无论C级还是E级，人家也供给全新M264 2.0T发动机，但价格什么水平，大家心里都有杆秤...</p> \n" +
                "<p class=\"ql-align-center\"><img src=\"http://5b0988e595225.cdn.sohucs.com/images/20181224/d708124bf4854d7ab3ae884f678b750c.jpeg\" max-width=\"600\"></p> \n" +
                "<h1>说在最后</h1> \n" +
                "<p>总的来说，作为国内综合实力最强大的三个豪华品牌，希望奥迪未来能把更多优质进口车引进国内，感觉BBA中，奥迪进口车销量水平还是很强势的；宝马呢，全新5系、全新X3引领的新一代车型产品力几乎无可挑剔，但作为新能源领域领军人（或者说豪华品牌最早深耕新能源领域甚至推出独立新能源子品牌）近来动作有些滞后；奔驰品牌力依旧如日中天，但1.5T动力的应用无论如何还是抹杀了一部分好感。</p> \n" +
                "<p>总的来说，BBA的2018总归是亮点比槽点多，这不总结完槽点，下期也该集中整理“亮点篇”了。这一年中，BBA扎堆向国内市场投放了很多重磅新车，也有不少针对中国消费者的福利政策，具体先不剧透，咱下周逐一点评。</p>";
        List<KeyWordLocation> keyWordLocations = searchTree.analyzeFromString(content1, 5);
        long e = System.currentTimeMillis();
        keyWordLocations.stream().forEach(i-> System.out.println(i));

        logger.info("total time cost {} ms",e-s);

    }

}
