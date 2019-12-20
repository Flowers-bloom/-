/**
 * 当涉及产品族时，需要引入抽象工厂模式
 * <p>
 * 比如我们要造一台电脑
 * 如果我们继续采用工厂模式，那么我们会有CPU工厂（派生intelCpu，amdCpu），主板工厂（派生intelBoard，amdBoard）......
 * 然后客户端再将这些构件组装在一起。
 * 如此方法，优点：如果要增加构件，易于扩展
 * 缺点：由于客户端的自定义组装，可能出现如CPU与Board不兼容使用的情况
 * <p>
 * 如果采用抽象工厂模式，我们会抽象一个Computer工厂（派生不同品牌电脑interComputer，amdComputer），在工厂内定义电脑的构件搭配
 * 如此方法，优点：解决构件不兼容的情况
 * 缺点：如果要增加构件，需要修改每个工厂，给每种品牌的电脑都加上该新增构件的方法，不易于扩展，有点违背对修改关闭，对扩展开放的原则
 */
public interface Computer {
    CPU makeCPU();

    MainBoard makeMainBoard();
}

public class IntelComputer implements Computer {
    @Override
    public CPU makeCPU() {
        return new CPU;
    }

    @Override
    public MainBoard makeMainBoard() {
        return new MainBoard();
    }
}

public class AmdComputer implements Computer {
    @Override
    public CPU makeCPU() {
        return new CPU;
    }

    @Override
    public MainBoard makeMainBoard() {
        return new MainBoard();
    }
}

public class APP {
    public static void main(String[] args) {
        // 第一步就要选定一个“大厂”
        ComputerFactory cf = new AmdFactory();
        // 从这个大厂造 CPU
        CPU cpu = cf.makeCPU();
        // 从这个大厂造主板
        MainBoard board = cf.makeMainBoard();

        // 将同一个厂子出来的 CPU、主板、硬盘组装在一起
        Computer result = new Computer(cpu, board);
    }
}
