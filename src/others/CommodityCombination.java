package others;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-03-08 15:04<p>
 * 阿里巴巴java开发实习网上编程测试：
 * <p>
 * 一个淘宝的订单中包含n(10>=n>=1)种商品A1，A2，...，An，每种商品数量分别为a1，a2，...，an个，记做{a1,a2,...,an}(ak>0)。<p>
 * 订单在仓库生产过程中，仓库为了提升作业效率，会提前对热门组合商品进行预包装。<p>
 * 假设这n个商品有m(9>=m>=1)个商品组合，每个组合bomk包含A1，A2，...，An的数量分别为{b1,b2,...,bn}(bk>=0,至少存在一个bk>0)<p>
 * 举例如下：<p>
 * 订单包含A，B，C商品，数量为{2，3，1}，商品组合bom1{2，1，1}，bom2{1，1，0}，bom3{0，1，1}<p>
 * <p>
 * 对以上订单匹配给定商品组合，得到的可能匹配结果为：res1.匹配到组合1一套，剩余B商品；res2.匹配到组合2两套，组合3一套，不剩商品；<p>
 * 现要求订单的最优匹配，最优匹配的原则为：1.匹配组合后，剩余商品种类数越少越好；2.在剩余商品种类数相同的情况下，匹配到的组合种类数越少越好；<p>
 * 例如上面例子，我们认为res2优于res1。<p>
 * <p>
 * 现需要编写程序，输入格式为：<p>
 * n,m<p>
 * a1,a2,...,an<p>
 * bom1,b11,b12,...,b1n<p>
 * bom2,b21,b22,...,b2n<p>
 * ....<p>
 * bomm,bm1,bm2,...,bmn<p>
 * <p>
 * 输入数据的格式说明（数据间使用英文逗号分隔）：<p>
 * 第一行数据：n个商品，m个预包方案<p>
 * 第二行数据：商品1个数，商品2个数，。。。，商品n个数<p>
 * 第三行数据：bom1，商品1个数，商品2个数，。。。，商品n个数<p>
 * 第n-1行数据：。。。。<p>
 * 第n行数据：bomn，商品1个数，商品2个数，。。。，商品n个数<p>
 * <p>
 * 针对输入数据找出最优匹配，输出最优匹配的组合及套数，比如针对上面的例子输出：<p>
 * match result:<p>
 * bom2*2，bom3*1<p>
 * 注：输出结果有多个时可以乱序<p>
 */

import java.util.*;

public class CommodityCombination {

    private Map<List<Integer>, Problem> optimalRes;
    private List<Integer> order = new ArrayList<>();
    private Map<String, List<Integer>> boms = new HashMap<>();

    public CommodityCombination(List<Integer> order, Map<String, List<Integer>> boms) {
        this.order = order;
        this.boms = boms;
        optimalRes = new HashMap<>();
    }

    private class Problem {

        List<Integer> name;
        String nextBom;
        Integer reminderNumber;
        Integer bomNumber;

        Problem(List<Integer> name) {
            this.name = name;
            reminderNumber = Integer.MAX_VALUE;
        }
    }

    public static void main(String[] args) {

        List<Integer> order = new ArrayList<Integer>();
        Map<String, List<Integer>> boms = new HashMap<String, List<Integer>>();

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        Integer n = Integer.parseInt(line.split(",")[0]);
        Integer m = Integer.parseInt(line.split(",")[1]);

        line = in.nextLine();
        String[] itemCnt = line.split(",");
        for (int i = 0; i < n; i++) {
            order.add(Integer.parseInt(itemCnt[i]));
        }

        for (int i = 0; i < m; i++) {
            line = in.nextLine();
            String[] bomInput = line.split(",");
            List<Integer> bomDetail = new ArrayList<>();

            for (int j = 1; j <= n; j++) {
                bomDetail.add(Integer.parseInt(bomInput[j]));
            }
            boms.put(bomInput[0], bomDetail);
        }
        in.close();

        CommodityCombination main = new CommodityCombination(order, boms);
        Map<String, Integer> res = main.resolve();

        System.out.println("match result:");
        for (String key : res.keySet()) {
            System.out.println(key + "*" + res.get(key));
        }
    }

    public Map<String, Integer> resolve() {
        Map<String, Integer> res = new HashMap<>();
        for (String s : boms.keySet()) {
            res.put(s, 0);
        }

        resolveIteration(order);

        Problem subProblem = optimalRes.get(order);
        while (subProblem.nextBom != null) {
            res.put(subProblem.nextBom, res.get(subProblem.nextBom) + 1);

            List<Integer> newOrder = new ArrayList<>();
            for (int i = 0; i < order.size(); i++) {
                newOrder.add(order.get(i) - boms.get(subProblem.nextBom).get(i));
            }
            order = newOrder;
            subProblem = optimalRes.get(newOrder);
        }

        return res;
    }

    private void resolveIteration(List<Integer> order) {
        Problem optimalProblem = new Problem(order);
        Set<Map.Entry<String, List<Integer>>> bomsEntrySet = boms.entrySet();
        Boolean noNextBom = true;

        loop:
        for (Map.Entry<String, List<Integer>> entry : bomsEntrySet) {

            List<Integer> newOrder = new ArrayList<>();
            String bom = entry.getKey();
            for (int i = 0; i < order.size(); i++) {
                Integer tmp = order.get(i) - entry.getValue().get(i);
                if (tmp >= 0) {
                    newOrder.add(tmp);
                } else {
                    continue loop;
                }
            }

            if (optimalRes.get(newOrder) == null) {
                resolveIteration(newOrder);
            }

            Problem nextSubProblem = optimalRes.get(newOrder);
            if (nextSubProblem.reminderNumber < optimalProblem.reminderNumber ||
                    (nextSubProblem.reminderNumber.equals(optimalProblem.reminderNumber) && nextSubProblem.bomNumber < optimalProblem.bomNumber)) {
                optimalProblem.reminderNumber = nextSubProblem.reminderNumber;
                optimalProblem.bomNumber = nextSubProblem.bomNumber + 1;
                optimalProblem.nextBom = bom;
            }

            noNextBom = false;
        }

        if (noNextBom) {
            optimalProblem.nextBom = null;
            optimalProblem.bomNumber = 0;
            optimalProblem.reminderNumber = 0;
            for (Integer integer : order) {
                if (integer > 0) {
                    optimalProblem.reminderNumber++;
                }
            }
        }

        optimalRes.put(order, optimalProblem);
    }

}

