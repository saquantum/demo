## java.awt+java.swing交互界面

```
package newPackage;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;

public class GameJFrame extends JFrame implements MouseListener {
    private final int PICTURES = 16;
    private String currentPuzzle = "testModule\\src\\image\\animal\\animal3\\";
    private int[][] array = new int[4][4];
    private JLabel[][] images = new JLabel[4][4];

    public GameJFrame(){
        // 初始化窗口
        this.initFrame();
        // 初始化菜单栏
        this.initMenu();
        // 打乱图片顺序
        this.randomizeImages();
        // 初始化图片
        this.initImages();
        // 初始化键盘快捷键
        this.initShortcuts();
        // 最后设置为可见窗口
        this.setVisible(true);
    }

    private boolean wins(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(this.array[i][j] != j*4+i+1){
                    if(i==3 && j==3 && this.array[i][j]==0){
                    }else{
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void initShortcuts(){
        this.setFocusable(true);  // Make sure JFrame is focusable
        this.requestFocusInWindow();  // Request focus

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode == 27){
                    // 按ESC会关闭窗口
                    GameJFrame.this.dispose();
                }else if(keyCode == 'A'){
                    // 按a会启用作弊功能
                    System.out.println("autowin");
                    autowin();
                }

            }
        });
    }

    private void initImages(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                int n = this.array[i][j];
                JLabel label;
                if(n == 0){
                    label = new JLabel();
                }else{
                    label = new JLabel(new ImageIcon( this.currentPuzzle + n + ".jpg"));

                }
                label.setBorder(new BevelBorder(1));
                label.addMouseListener(this);
                label.setBounds(i*105,j*105,105,105);
                this.images[i][j] = label;
                this.getContentPane().add(label);
            }
        }
        if(this.wins()){
            System.out.println("you win");
        }
    }


    private void initFrame(){
        // 设置尺寸
        this.setSize(603, 700);
        // 设置居中
        this.setLocationRelativeTo(null);
        // 关闭窗口后后台停止运行
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 取消默认布局
        this.setLayout(null);
    }

    private void initMenu(){
        // 新建菜单栏对象
        JMenuBar menuBar = new JMenuBar();
        // 新建下拉菜单
        JMenu functionality = new JMenu("options");
        JMenu info = new JMenu("info");
        // 新建下拉菜单选项
        JMenuItem restartItem = new JMenuItem("restart game");
        JMenuItem currentAutoWinItem = new JMenuItem("current player wins");
        JMenuItem information = new JMenuItem("information");
        // 绑定选项至下拉菜单
        functionality.add(restartItem);
        functionality.add(currentAutoWinItem);
        info.add(information);
        // 绑定下拉菜单至菜单栏
        menuBar.add(functionality);
        menuBar.add(info);
        // 将当前菜单绑定至窗口
        this.setJMenuBar(menuBar);
    }

    private void randomizeImages(){
        int[] tmp = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r = new Random();
        for (int i = 0; i < tmp.length; i++) {
            int j = r.nextInt(tmp.length);
            int t = tmp[j];
            tmp[j] = tmp[i];
            tmp[i] = t;
        }
        System.out.println(Arrays.toString(tmp));
        for(int n = 0; n < tmp.length; n++) {
            this.array[n/4][n%4] = tmp[n];
        }
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void removeAllImages(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                JLabel label = this.images[i][j];
                for (MouseListener mouseListener : label.getMouseListeners()) {
                    label.removeMouseListener(mouseListener);
                }
                this.remove(label);
            }
        }
    }

    private void autowin(){
        removeAllImages();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                this.array[i][j] = j*4+i+1;
            }
        }
        this.array[3][3] = 0;
        this.revalidate();  // Refresh the layout
        this.repaint();  // Repaint the frame
        initImages();
    }

    private void swapImages(JLabel label){
        // find the label in the array
        int x=-1, y=-1;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(this.images[i][j].equals(label)){
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        if(x==-1 || y==-1){
            return;
        }
        JLabel emptyTile = null;
        int newX = -1, newY = -1;
        if(y>0 && this.array[x][y-1]==0){
            System.out.println("move up");
            emptyTile = this.images[x][y-1];
            newX = x;
            newY = y-1;
        }else if(y<3 && this.array[x][y+1]==0){
            System.out.println("move down");
            emptyTile = this.images[x][y+1];
            newX = x;
            newY = y+1;
        }else if(x>0 && this.array[x-1][y]==0){
            System.out.println("move left");
            emptyTile = this.images[x-1][y];
            newX = x-1;
            newY = y;
        }else if(x<3 && this.array[x+1][y]==0){
            System.out.println("move right");
            emptyTile = this.images[x+1][y];
            newX = x+1;
            newY = y;
        }
        if (newX == -1 || newY == -1) {
            return;
        }
        // Swap labels in the UI
        JLabel temp = this.images[x][y];
        this.images[x][y] = this.images[newX][newY];
        this.images[newX][newY] = temp;

        // Swap values in the array
        int tempVal = this.array[x][y];
        this.array[x][y] = this.array[newX][newY];
        this.array[newX][newY] = tempVal;

        // Update the label positions
        this.images[x][y].setBounds(x * 105, y * 105, 105, 105);
        this.images[newX][newY].setBounds(newX * 105, newY * 105, 105, 105);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {
        this.swapImages((JLabel) e.getSource());
        if(this.wins()){
            System.out.println("you win");
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        source.setBorder(new BevelBorder(0));
    }
    @Override
    public void mouseExited(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        source.setBorder(new BevelBorder(1));
    }
}

```



## 继承

可继承的内容

|          |                      |                  |
| -------- | -------------------- | ---------------- |
| 构造方法 | non-private 不能继承 | private 不能继承 |
| 变量     | non-private 可以继承 | private 可以继承 |
| 方法     | non-private 不能继承 | private 可以继承 |

就近原则: 方法在访问变量时, 按照方法内局部变量->本类变量->父类变量查询. `var`->`this.var`->`super.var`

方法重写: 子类继承父类的虚函数表, 并添加本类中的方法. 若发生了重写则覆盖虚函数表中的函数指针.

1. 只有虚方法才能被加入到虚函数表. 非 `private` 非 `static` 非 `final` 的方法被称为虚方法.
2. 子类中重写的方法的访问权限必须大于等于原来的, 返回值类型必须小于等于原来的.
3. 重写方法的名称和形参列表必须完全一致.

构造方法: 首先默认调用父类的构造方法( `super(<argumentList>);` 递归调用父类的构造方法), 然后再调用本类的构造方法.

`this`和`super`

| 关键字  | 访问变量    | 访问方法            | 访问构造方法 |
| ------- | ----------- | ------------------- | ------------ |
| `this`  | `this.var`  | `this.method(...)`  | `this(...)`  |
| `super` | `super.var` | `super.method(...)` | `super(...)` |



## 多态

`Animal a = new Dog();`

引用类型: 等号左边

实际类型: 等号右边, 调用了构造方法.

1. 实际类型需要`extends`或`implements`引用类型
2. 调用变量或方法时, 需要引用类型中具有对应的变量或方法.
3. 调用变量或方法时, 被调用的变量来自引用类型, 方法来自实际类型.
4. 类型转换只会改变引用类型.



## 抽象类

1. 不能创建抽象类对象
2. 有抽象方法的类必须是抽象类
3. 抽象类可以有构造方法
4. 抽象类的子类要么是抽象类, 要么重写所有的抽象方法



## 接口

1. 变量只能是常量 `static final`
2. 没有构造方法
3. 定义抽象方法可以不加`abstract`
4. 定义有方法体的方法需要加`default`
5. 被默认方法调用的接口内方法可以加`private`



## Lambda表达式

```
Arrays.sort(arr, new Comparator<Integer>(){
	@Override
	public int compare(Integer o1, Integer o2){
		return o1 - o2;
	}
});

()->{}

Arrays.sort(arr, (Integer o1, Integer o2)->{
		return o1 - o2;
	}
);

Arrays.sort(arr, (o1, o2)-> o1 - o2);
```

lambda表达式只能简化函数式接口(只有一个抽象方法的接口)的匿名内部类

lambda表达式实际上就是函数式接口的匿名实现类

如果匿名内部类的实现方法的方法体只有一行, 可以省略成最简形式



## JAVA的数据结构API

`Collection<E>` 单列集合的接口

​	`List<E>` 元素有序 可重复 有索引

​		`ArrayList<E>` 动态数组

​		`LinkedList` 双向链表

​	`set<E>` 元素无序 不可重复 无索引

​		`TreeSet` 红黑树

​		`HashSet` 拉链法哈希表

​		`LinkedHashSet` 拉链法哈希表 + 记录插入顺序的链表

遍历方式:

1. 迭代器

   ```
   Collection<E> list = new ArrayList<>();
   Iterator<E> it = list.iterator();
   while(it.hasNext()){
   	E e = it.next();
   	...
   }
   ```

2. 增强for (简化版的迭代器)

   ```
   Collection<E> list = new ArrayList<>();
   for(E e : list){
   	...
   }
   ```

3. lambda (使用forEach方法)

   `void forEach(Consumer<? super E> action);`

   ```
   list.forEach(new Consumer<E>{
   	@Override
   	public void accept(E e){
   		...
   	}
   } );
   
   list.forEach( (e) -> {...} )
   ```

如果遍历中需要删除元素, 使用迭代器.

如果遍历中需要添加元素, 使用列表迭代器`ListIterator`



`Map<K, V>` 双列集合的接口

​	`TreeMap`

​	`HashMap`

​	`LinkedHashMap`



`Map`中的方法

```
V put(K key, V value) 如果key已经存在, 则会覆盖已存在的KV对并返回已存在的V值
V get(K key) 返回key对应的值, 不存在时返回null
V remove(Object key)
boolean containsKey(Object key)
boolean containsValue(Object value)
boolean isEmpty()
int size()
```

`Map`的遍历

1. 使用`keySet`方法提取所有的键

```
Map<K, V> map = new HashMap<>();
Set<K> keys = map.keySet();
for(K key : keys){
	V value = map.get(key);
	...
}
```

2. 使用`entrySet`方法提取所有键值对

```
Set<Map.Entry<K, V>> entries = map.entrySet();
for(Map.Entry<K, V> entry : entries){
	K key = entry.getKey();
	V value = entry.getValue();
	...
}
```

`Entry` 是`Map`的内部接口.

3. lambda (`forEach`方法)

`void forEach(Biconsumer<? super K, ? super V> action)`

```
map.forEach(new Biconsumer<K, V>(){
	@Override
	public void accept(K key, V value){
		...
	}
})

map.forEach( (key, value) -> {...} )
```



## Stream

生成`Stream`流对象的方法:

|          |                                                              |
| -------- | ------------------------------------------------------------ |
| 单列集合 | 调用Collection接口中的静态方法 `Stream<E> stream()`          |
| 双列集合 | 不能直接使用stream, 需要先转换为`keySet` 或`entrySet`        |
| 数组     | 使用Arrays工具类中的静态方法 `public static <E> Stream<E> stream(E[] a) ` |
| 零散数据 | 使用Stream接口中的静态方法`public static <E> Stream<E> of(E... values) ` |

`Stream`中间方法(仍然返回`Stream`对象):

|                                                    |                                                              |
| -------------------------------------------------- | ------------------------------------------------------------ |
| `Stream<E> filter(Predicate<? super E> predicate)` | 重写函数式接口`Predicate`中的`test`方法                      |
| `Stream<E> limit(long n)`                          | 仅获取前`n`个元素                                            |
| `Stream<E> skip(long n)`                           | 跳过前`n`个元素                                              |
| `Stream<E> distinct()`                             | 删除重复元素, 需要重写`hashCode`和`equals`                   |
| `static <E> Stream<E> concat(Stream a, Stream b)`  | 合并两个流, 新的数据类型是两者的共同父类                     |
| `Stream<E> map(Function<T, E> mapper)`             | 重写函数式接口`Function`中的`apply`方法, 将原本的流`Stream<T>`进行类型转换为`Stream<E>` |

```
ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "abc-1", "cde-2", "asg-3", "hjasdgh-4", "avfga-5");

list.stream()
		.filter(s -> "abc".equals(s.split("-")[1]))
		.map(s->Integer.parseInt(s.split("-")[1]))
		.forEach(System.out::println);
```

终结方法

|                                                              |                          |
| ------------------------------------------------------------ | ------------------------ |
| `void forEach(Consumer<? super E> action)`                   | 迭代器                   |
| `long count()`                                               | 统计流中的元素个数       |
| `Object[] toArray()` 或 `A[] toArray(IntFunction<A[]> generator)` | 将流中的元素收集到数组中 |
| `collect(Collector c)`                                       | 将流中的元素收集到集合中 |

```
list.stream().toArray(length -> new String[length]);

list.stream().collect(Collector.toList()).forEach(s -> System.out::println);

list.stream()
                .collect(Collectors.toMap(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return s.split("-")[0];
                    }
                }, new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        return Integer.parseInt(s.split("-")[1]);
                    }
                }))
                .forEach((key,value)->System.out.println(key + "=" + value));
                
list.stream()
                .collect(Collectors.toMap(s -> s.split("-")[0]
                	, s -> Integer.parseInt(s.split("-")[1])
                .forEach((key,value)->System.out.println(key + "=" + value));
```



## 方法引用

1. 引用处必须是函数式接口
2. 被引用的方法的形参和返回类型需要和接口的抽象方法一致



引用静态方法: `CLASSNAME::METHODNAME`

```
list.stream().map(Integer::parseInt).forEach(System.out::println);
```

用类名引用成员方法: `CLASSNAME::METHODNAME`

​	此时需要抽象方法的第二个到最后一个形参与被引用的方法一致.

```
list.stream().map(String::toUpperCase).forEach(System.out::println);
```

用对象引用成员方法: `OBJECTNAME::METHODNAME` 或 `this::METHODNAME` 或 `super::METHODNAME`

```
list.stream().map(new test()::filter1).forEach(System.out::println);
public boolean filter1(String s){return ...}
```

引用类的构造方法: `CLASSNAME::new` 

```
list.stream().map(Student::new)
```

引用数组的构造方法: `TYPE::new`

```
list.stream().toArray(String[]::new);
```



## 异常

`try-catch-finally` : 运行`try`中代码, 若遇到异常, 则查找对应的捕获块`catch`并执行其中代码. 无论是否捕获, 最后执行`finally`块中代码.

1. 如果`try`中没有遇到异常, 则会执行完毕`try`中全部代码, 跳过`catch`
2. 如果`try`中可能遇到多个异常, 则需要添加多个`catch`块, 并且父类异常需要写在子类下面
3. 如果`try`中遇到的异常没有被捕获, 则交给JVM在控制台报错
4. 如果`try`中遇到异常后还有没执行的代码, 则会被跳过

`Throwable`接口中的方法

|                        |                        |
| ---------------------- | ---------------------- |
| `String getMessage()`  | 返回可抛出的信息       |
| `String toString()`    | 返回可抛出的名字和信息 |
| `void printStackTrace` | 在控制台打印异常信息   |

自定义异常:

```
public class NumberFormatException extends RuntimeExcepion{
	public NumberFormatException(){}
	
	public NumberFormatException(String message){ super(message); }
}
```

抛出异常:

`throw`: 用在方法体中

```
if(arr == null){
	throw new NullPointerException();
}
```

`throws`: 用在方法的定义中

```
public void method() throws NumberFormatException{ ... }
```



## 文件与IO

文件对象的构造函数

|                                     |                                                |
| ----------------------------------- | ---------------------------------------------- |
| `File(String path)`                 | 根据路径字符串创建文件对象                     |
| `File(String parent, String child)` | 根据父路径和子路径字符串创建文件对象           |
| `File(File parent, String child)`   | 根据父路径的文件对象和子路径字符串创建文件对象 |

文件对象的方法

​	判断文件的性质:

```
boolean isDirectory()
boolean isFile()
boolean exists()
```

​	返回文件的属性:

```
long length() 返回文件的字节数
String getAbsolutePath()
String getPath() 返回创建文件对象时使用的路径
String getName()
long lastModified() 返回文件的最后修改时间
```

​	创建文件或文件夹:

```
boolean createNewFile()
boolean mkdir() 创建单层文件夹
boolean mkdirs() 创建多级文件夹
boolean delete() 不能直接删除有内容的文件夹
```

​	遍历方法:

```
File[] listFiles() 获取当前路径下所有文件与文件夹
```

  1. 若路径不存在或者不是文件下, 返回`null`

  2. 若需要权限才能访问文件夹, 返回`null`

  3. 若路径是空文件夹, 返回长度为0的数组

     ```
     	public static void main(String[] args){
             File f = new File("D:\\Driver");
             Map<String, Integer> map = new HashMap<>();
             recursion(map, f);
             map.forEach((key, value)->{
                 System.out.println(key + "=" + value);
             });
         }
     
         public static void recursion(Map<String, Integer> map, File f){
             if(!f.exists()){
                 return;
             }
             if(f.isFile()){
                 String[] a = f.getName().split("\\.");
                 String extension = a[a.length-1].toLowerCase();
                 if(!map.containsKey(extension)){
                     map.put(extension, 1);
                 }else{
                     map.put(extension, map.get(extension)+1);
                 }
             }else{
                 File[] subs = f.listFiles();
                 for(File file : subs){
                     recursion(map, file);
                 }
             }
         }
     ```

     其他遍历方法:

```
static File[] listRoots() 获取文件系统根或者所有盘符
String[] list() 获取当前路径下所有文件与文件夹的名字
File[] listFiles(FileFilter filter) 利用过滤器接口获取当前路径下符合要求的文件与文件夹
```

IO流的分类:

​	字节流: 用于所有文件

​		字节输入流`InputStream`

​		字节输出流`OutputStream`

​	字符流: 用于纯文本文件

​		字符输入流`Reader`

​		字符输出流`Writer`



文件字节输出流`FileOutputStream`: 如果没有打开续写, 创建时会清空对象文件. 不要在文件字节输入流后面立刻创建文件字节输出流.

​	构造函数可以接收路径字符串或者文件对象

​	续写: 构造函数的第二个参数

```
FileOutputStream fos = new FileOutputStream(PATH, true);
byte[] bytes = "abc".getBytes();
fos.write(bytes);
fos.close();
```

文件字节输入流`FileInputStream`

一次读取一个字节:

```
FileInputStream fis = new FileInputStream(PATH);
int b;
while( (b = fis.read()) != -1){
	System.out.println((char) b);
}
fis.close();
```

一次读取多个字节:

```
FileInputStream fis = null;
FileOutputStream fos = null;
try{
	fis = new FileInputStream(PATH);
	fos = new FileOutputStream(PATH, true);
	int len;
	byte[] bytes = new byte[1024*1024*5];
	while( (len = fis.read(bytes)) != -1){
		fos.write(bytes, 0, len);
	}
}catch(IOException e){
	e.printStackTrace();
}finally{
	if(fos!=null){
		try{fos.close();}catch(IOException e){e.printStackTrace();}
	}
	if(fis!=null){
		try{fis.close();}catch(IOException e){e.printStackTrace();}
	}
}
```

字符集: ascii, GBK, Unicode

GBK编码方式: 英文以0开头占1字节, 中日韩以1开头占2字节

UTF-8编码方式: 英文以0开头占1字节, 中文占3字节, 第一个字节以1110开头, 后2个字节以10开头

`String`中编码和解码的方法:

|                                        |                        |
| -------------------------------------- | ---------------------- |
| `byte[] getBytes()`                    | 使用默认方式进行编码   |
| `byte[] getBytes(String charset)`      | 使用指定字符集进行编码 |
| `String(byte[] bytes)`                 | 使用默认方式进行解码   |
| `String(byte[] bytes, String charset)` | 使用指定字符集进行解码 |

读写编码转换:

```
FileReader fr = new FileReader(src, Charset.forname("GBK"));
FileWriter fw = new FileWriter(dest, Charset.forname("UTF-8"));
int b;
while((b == fr.read()) != -1){
	fw.write(b);
}
fw.close();
fr.close();
```

文件字符输入流`FileReader`和文件字符输出流`FileWriter`: 用法和字节输入输出一样, 需要把字节数组`bytes`换成字符数组`char[] cbuf`. 输入流可以接收字符串. 文件字符输入流创建时会创建一个8192字节的缓冲区并从文件中尽可能多地读取数据以填满缓冲区. 输入程序时会读取缓冲区, 若缓冲区读完了就会再次访问文件并再次填满缓冲区. 文件字符输出流也有缓冲区, 并且会在缓冲区填满之后再把数据输出到文件中. 要手动刷新缓冲区, 可以使用`flush()`方法.



字节缓冲流: `BufferedInputStream(InputStream is)`, `BufferedOutputStream(OutputStream os)`: 输入输出时都先填满默认长度8192的缓冲区再从缓冲区调取数据.用法与基本流一样. 要修改缓冲区长度, 使用构造函数的第二个参数.

字符缓冲流: `BufferedReader(Reader r)`, `BufferedWriter(Writer r)` . 用法与基本流一致, 但二者的默认缓冲区长度是8192个字符(8192*2个字节). 要修改缓冲区长度, 使用构造函数的第二个参数. 两个独有方法: 字符缓冲输入流的`String readLine()`一次读取一整行数据(但不会读取换行符). 读取到文件末尾的返回值不是`-1`而是`null`. 可以调用字符缓冲输入流自带的`void newLine()`来得到换行符, 或者使用`System.lineSeparator()`方法.



转换流: `InputStreamReader(InputStream is)` 将字节输入流转换为字符输入流.`OutputStreamWriter(OutputStream os)`将字符输出流转换为字节输出流. 作用: 在用字节流读写时想使用字符流的特性.

利用字节流读取文件, 每次读一整行并保持编码:

```
BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)));
String line;
while((line = br.readLine()) != null){
	...
}
br.close();
```



序列化流与反序列化流: 将java对象写入到文件中.

|                                       |                                                         |
| ------------------------------------- | ------------------------------------------------------- |
| `ObjectOutputStream(OutputStream os)` | 序列化流的构造方法                                      |
| `final void writeObject(Object obj)`  | 把对象写入文件. 被写入的对象需要实现`Serializable`接口. |
| `ObjectInputStream(InputStream is)`   | 反序列化流的构造方法                                    |
| `Object readObject()`                 | 读取被序列化的本地文件中的对象, 并返回Object对象.       |

1. 如果在序列化后对java类进行了修改, 则反序列化会报错. 解决: 给java类添加`private static final long serialVersionUID` 变量.
2. 如果不想让某个变量被序列化, 则需要添加修饰符`transient`.
3. 当一个本地文件中要保存多个对象时, 最好使用集合将他们保存为一个整体, 方便取出.

```
ArrayList<Student> list = new ArrayList<>();
list.add(s1);list.add(s2);

ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
oos.writeObject(list);
oos.close();

ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
ArrayList<Student> list = (ArrayList<Student>) ois.readObject();
ois.close();
```



字节打印流`PrintStream`: `System.out`对象所属的类. 可以使用`print`, `printf`和`println`方法.

字符打印流`PrintWriter`: 方法与字节打印流一致. 字节打印流没有缓冲区, 不需要打开自动刷新; 字符打印流有缓冲区, 如果要自动刷新则需要在构造函数中开启.



压缩流`ZipOutputStream`和解压缩流`ZipInputStream`: 只支持`.zip`文件

```
public static void main(String[] args){
	String PATH = ...
	File src = new File(PATH);
    File dest = new File(src.getParentFile(), src.getName() + ".zip");
    toZip(src, new ZipOutputStream(new FileInputStream(dest)), src.getName());
    unZip(dest, src);
}

public static void unZip(File src, File dest) throws IOException{
	ZipInputStream zis = new ZipInputStream(new FileInputStream(src));
	
	ZipEntry entry;
	while((entry = zis.getNextEntry()) != null){
		if(entry.isDirectory()){
			File f = new File(dest, entry.toString());
			f.mkdirs();
		}else{
			BufferedOutputStream bos = BufferedOutputStream(new FileOutputStream(new File(dest, entry.toString())));
			int b;
			while((b = zis.read()) != -1){
				bos.write(b);
			}
			bos.close();
			zis.closeEntry();
		}
	}
	zis.close();
}

public static void toZip(File src, ZipOutputStream zos, String rootPath){
	File[] files = src.listFiles();
	if(files == null || files.length == 0){
    	return;
    }
	for(File f : files){
		if(f.isFile()){
			ZipEntry entry = new ZipEntry(rootPath + "\\" + f.getName());
			zos.putNextEntry(entry);
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(f));
			int b;
			while((b = fis.read()) != -1){
				zos.write(b);
			}
			fis.close();
			zos.closeEntry();
		}else{
			toZip(f, zos, rootPath + "\\" + f.getName());
		}
	}
}
```



利用IO流和正则表达式编写网络爬虫:

```
URL url = new URL("https://github.com/saquantum/j2c");
URLConnection c = url.openConnection();
BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));

StringBuilder sb;
int ch;
while((ch = br.read()) != -1){
	sb.append((char) ch);
}
br.close();

Matcher m = Pattern.compile("REGEX").matcher(sb.toString());
while(m.find()){
	System.out.println(m.group());
}
```



## 多线程

并发(concurrent): 多个指令在单个CPU上交替执行

并行(parallel): 多个指令在多个CPU上同时运行



线程的启动方式

```
第一种: 定义一个继承了Thread的类, 并重写run方法. 调用时, 使用start方法.
public class TestThread extends Thread{
	@Override
	public void run(){
		String threadName = getName();
		// codes...
	}
}
public class test{
	public static void main(){
		TestThread t = new TestThread();
		t.setName("Test Thread 1");
		t.start();
	}
}

第二种: 定义一个实现了Runnable接口的类, 并重写run方法. 调用时, 将类的实例传递给线程的构造函数.
public class TestRunnable implements Runnable{
	@Override
	public void run(){
		Thread t = Thread.currentThread();
		String threadName = t.getName();
		// codes...
	}
}
public class test{
	public static void main(){
		Thread t = new Thread(new TestRunnable());
		t.setName("Test Thread 1");
		t.start();
	}
}

第三种: 定义一个实现了Callable<E>接口的类, 并重写call方法. 调用时, 将类的实例传递给FutureTask的构造函数, 再传递给线程的构造函数.
public class TestCallable implements Callable<E>{
	@Override
	public E call(){
		Thread t = Thread.currentThread();
		String threadName = t.getName();
		// codes...
		return e;
	}
} 
public class test{
	public static void main(){
		FutureTask<E> ft = new FutureTask<>(new TestCallable());
		Thread t = new Thread(ft);
		t.setName("Test Thread 1");
		t.start();
		
		E result = ft.get();
	}
}
```

`Thread`中的常用方法

|                                    |                                                              |
| ---------------------------------- | ------------------------------------------------------------ |
| `String getName()`                 | 获取此线程的名字, 默认名字`Thread-N`                         |
| `void setName(String name)`        | 设置此线程的名字                                             |
| `static Thread currentThread()`    | 获取当前线程的对象. `main`方法在`main`线程中                 |
| `static void sleep(long time)`     | 让线程休眠以毫秒为单位的时间                                 |
| `void setPriority(int p)`          | 设置线程优先级. `1 <= p <= 10`. 优先级越高, 抢到CPU的概率越高. |
| `final int getPriority()`          | 获取线程优先级                                               |
| `final void setDaemon(boolean on)` | 设置为守护线程. 当其他非守护线程结束后, 守护线程会陆续结束.  |
| `static void yield()`              | 让出当前线程的CPU执行权, 使线程的运行更均匀.                 |
| `static void join()`               | 将此线程插入到当前线程之前.                                  |

线程锁: 多个线程调用共享的数据时, 数据会不同步.

```
同步代码块:
public class TestRunnable implements Runnable{
	@Override
	public void run(){
		synchronized(TestRunnable.class){
			// codes...
		}
	}
}

同步方法:
public class TestRunnable implements Runnable{
	@Override
	public void run(){
		E e = method();
		// ...
	}
	private synchronized E method(){
		// codes...
	}
}

锁对象(比synchronized更灵活):
class TestCall implements Callable<String>{

    static int number = 0;
    static Lock lock = new ReentrantLock();

    @Override
    public String call(){
        Thread t = Thread.currentThread();
        String threadName = t.getName();
        while(true){
            lock.lock();
            try{
                if(number>=100){
                    break;
                }
                number++;
                System.out.println(t.getName() + ".number = " + number);
            }finally{
                lock.unlock();
            }
        }
        return null;
    }
}
public class test {
    public static void main(String[] args) {
        new Thread(new FutureTask<>(new TestCall())).start();
        new Thread(new FutureTask<>(new TestCall())).start();
        new Thread(new FutureTask<>(new TestCall())).start();
    }
}
1. 锁对象必须是静态的, 否则多个线程对应于不同的锁对象.
2. 将unlock方法置于finally中, 可以保证锁被打开.
```

要将变量传递给线程, 可以使用非空构造方法来接收变量.



死锁: 两个锁相互嵌套, 导致程序卡死.



等待唤醒机制: 如果把制造数据和收集数据写在单个线程中, 可能会导致程序瓶颈. 当一个线程负责制造数据, 另一个线程负责收集数据时, 可能会出现过剩或者丢失的数据. 使用等待唤醒机制可以解决.

继承于`Object`的方法:

|                    |                                    |
| ------------------ | ---------------------------------- |
| `void wait()`      | 让当前线程等待, 直到被其他线程唤醒 |
| `void notify()`    | 随机唤醒一个线程                   |
| `void notifyAll()` | 唤醒所有线程                       |

用锁对象的`Condition`对象的`await()`和`signal()`方法:

```
public class test {
    public static void main(String[] args) {
        FutureTask<Object> ft = new FutureTask<>(new Consumer());
        new Thread(ft).start();
        new Thread(new FutureTask<String>(new Producer())).start();
    }
}
class LoggingTable {
    private static final LinkedList<String> loggingTable = new LinkedList<>();
    public static int count = 0;
    public static Lock lock = new ReentrantLock();
    public static final Condition notEmpty = lock.newCondition();
    public static final Condition notFull = lock.newCondition();
    public static void enqueue(String s) { loggingTable.add(s); }
    public static String dequeue() { return loggingTable.remove(0); }
    public static boolean isEmpty() { return loggingTable.isEmpty(); }
}
class Consumer implements Callable<Object> {
    static Lock lock = new ReentrantLock();
    @Override
    public Object call() throws Exception {
        while (true) {
            LoggingTable.lock.lock();
            try {
                if (LoggingTable.count >= 100) {
                    break;
                } else {
                    while (LoggingTable.isEmpty()) {
                        LoggingTable.notEmpty.await();
                    }
                    String s = LoggingTable.dequeue();
                    System.out.println(s + " is dequeued.");
                    LoggingTable.notFull.signalAll();
                }
            } finally {
                LoggingTable.lock.unlock();
            }
        }
        return null;
    }
}
class Producer implements Callable<String> {
    static Lock lock = new ReentrantLock();
    @Override
    public String call() throws Exception {
        while (true) {
            LoggingTable.lock.lock();
            try {
                if (LoggingTable.count >= 100) {
                    System.out.println("The logging system has received 100 users.");
                    break;
                } else {
                    // check whether logging queue is empty
                    while (!LoggingTable.isEmpty()) {
                        // not empty, wait
                        LoggingTable.notFull.await();
                    }
                    // empty queue, enqueue next String
                    String s = randomStringGenerator();
                    LoggingTable.enqueue(s);
                    System.out.println("User " + s + " is enqueued.");
                    LoggingTable.count++;
                    LoggingTable.notEmpty.signalAll();
                }
            } finally {
                LoggingTable.lock.unlock();
            }
        }
        return null;
    }
private String randomStringGenerator() {
        Random r = new Random();
        int len = r.nextInt(20) + 1;
        char[] chs = new char[len];
        int k = 0;
        while (k < len) {
            int tmp = r.nextInt(128);
            if (('a' <= tmp && tmp <= 'z') || ('A' <= tmp && tmp <= 'Z') || ('0' <= tmp && tmp <= '9')) {
                chs[k] = (char) tmp;
                k++;
            }
        }
        return new String(chs);
    }
}
```

用`wait()`和`notify()`: 将`Table.Condition.await()`换成`Table.lock.wait()`使线程与锁绑定, 并把用锁控制的代码`lock.lock()`改成同步代码块`synchronized(Table.lock){...}`.



线程的6种状态

|                          |                    |
| ------------------------ | ------------------ |
| 新建 `new`               | 创建线程对象       |
| 就绪 `runnable`          | `start()`          |
| 被阻塞 `blocked`         | 无法获得`Lock`对象 |
| 等待 `waiting`           | `wait()`           |
| 计时等待 `timed waiting` | `sleep()`          |
| 结束 `terminated`        | 运行完毕           |



线程池: 线程在结束之后不是关闭, 而是存入线程池等待复用, 节省资源.

|                                                              |                                   |
| ------------------------------------------------------------ | --------------------------------- |
| `Executors.` `static ExecutorService newCachedThreadPool()`  | 创建上限为`int`类型最大值的线程池 |
| `Executors.` `static ExecutorService newFixedThreadPool(int n)` | 创建上限为`n`的线程池             |
| `ExecutorService.` `Future<?> submit(Runnable task)`         | 提交任务                          |
| `ExecutorService.` `Future<E> submit(FutureTask<E> task)`    | 提交任务                          |
| `ExecutorService.` `void shutdown()`                         | 销毁线程池                        |

线程池的全参数构造方法:

```
ThreadPoolExecutuor(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
int corePoolSize: 核心线性数量
int maxPoolSize: 最大线程数 >=核心线程数
long keepAliveTime: 空闲线程最大存活时间
TimeUnit unit: 时间单位
BlockingQueue<Runnable> workQueue: 阻塞队列, 用于存放排队的线程
ThreadFactory threadFactory: 创建线程工厂
RejectedExecutionHandler handler: 拒绝策略
 
ThreadPoolExecutuor pool = new ThreadPoolExecutuor(3, 6, 60, TimeUnit.SECONDS, 
new ArrayBlockingQueue<>(3), 
Executors.defaultThreadFactory(), 
new ThreadPoolExecutor.AbortPolicy());
```

最大并行数: 能同时运行的最大线程数 `Runtime.getRuntime().availableProcessors()`

CPU密集型运算(计算多, 读取少): 线程池的最大线程数设为 最大并行数+1

IO密集型运算: 线程池的最大线程数设为 最大并行数\*期望CPU利用率\*(CPU计算时间+CPU等待时间)\*CPU计算时间



## 网络编程

网络三要素

|        |                            |
| ------ | -------------------------- |
| IP     | 设备在网络中的唯一标识     |
| 端口号 | 应用程序在设备中唯一的标识 |
| 协议   | http https ftp TCP UDP 等  |

本机的IP地址: 127.0.0.1

获取本机局域网IP: `InetAddress.getByName("DESKTOP-PF2B1AC").getHostAddress()`

UPD协议: 面向无连接通信协议, 一次最多发送64k数据.

```
发送信息: 创建DatagramSocket对象, 使用send方法发送DatagramPacket对象.
DatagramSocket ds = new DatagramSocket(); // 空参构造会随机分配可用的端口
byte[] bytes = "This is a message".getBytes();
ds.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), 8000));
ds.close();

接受信息: 创建DatagramSocket对象时指定端口, 使用receive方法接收DatagramPacket对象.
DatagramSocket ds = new DatagramSocket(8000);
byte[] bytes = new byte[1024];
DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
ds.receive(dp);
System.out.println("received message \"" + new String(dp.getData(), 0, dp.getLength()) + "\" from IP address " + dp.getAddress() + " 's port " + dp.getPort());
```

单播: 发送到一台设备上

组播: 发送到局域网中的一组设备上 预留地址: 224.0.0.0 ~ 224.0.0.255

广播: 发送给局域网中的所有设备 地址: 255.255.255.255 广播的发送代码与单播一致, 只需要修改IP. 接收广播不需要修改IP.

组播的发送和接受:

```
MulticastSocket ms = new MulticastSocket(); 
byte[] bytes = "This is a message".getBytes();
ms.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName("224.0.0.1"), 8000));
ms.close();

MulticastSocket ms = new MulticastSocket(8000);
ms.joinGroup(InetAddress.getByName("224.0.0.1"));
byte[] bytes = new byte[1024];
DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
ms.receive(dp);
System.out.println("received message \"" + new String(dp.getData(), 0, dp.getLength()) + "\" from IP address " + dp.getAddress() + " 's port " + dp.getPort());
```

TCP协议: 通信之前确保连接建立, 传输数据时使用IO流

三次握手: 客户端向服务器发出连接请求等待服务器确认, 服务器返回一个响应, 客户端再次发出确认信息建立连接.

四次挥手: 客户端发出取消连接请求, 服务器返回一个响应, 服务器处理完数据传输后再次发出确认取消连接消息, 客户端再次发送消息取消连接.

基本TCP通信代码: 使用`Socket`和`ServerSocket`对象

```
客户端:
Socket socket = new Socket("127.0.0.1", 8001);
BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
bw.write("886");
bw.flush(); // 需要手动刷新Buffered流的缓存, 否则信息将丢失
socket.close();

服务器:
ServerSocket ss = new ServerSocket(8001);
Socket socket = ss.accept();
BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
String line;
while((line = br.readLine()) != null){
	System.out.println(line);
}
socket.close();
ss.close();
```

多线程的TCP通信, 服务器可回传数据:

```
客户端:
public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 8001);
        BufferedReader file = new BufferedReader(new FileReader("E:\\Users\\Desktop\\index.html"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        String filecontents;
        while((filecontents = file.readLine()) != null){
            bw.write(filecontents + System.lineSeparator());
            bw.newLine();
        }
        bw.flush();
        file.close();
        s.shutdownOutput();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        s.close();
    }
}

服务器:
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8111);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        while (true) {
            Socket s = ss.accept();
            threadPool.submit(new receiverRunnable(s));
        }
        //ss.close();
    }
}
class receiverRunnable implements Runnable {
    Socket s;
    receiverRunnable(Socket s) {
        this.s = s;
    }
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter file = new BufferedWriter(new FileWriter(UUID.randomUUID().toString().replace("-", "") + ".html"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                file.write(line);
                file.newLine();
            }
            file.close();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write("server has received this massage.");
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

局域网网页服务器:

```
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println(InetAddress.getByName("DESKTOP-PF2B1AC").getHostAddress());
        ServerSocket ss = new ServerSocket(8111);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        while (true) {
            Socket s = ss.accept();
            threadPool.submit(new receiverRunnable(s));
        }
        //ss.close();
    }
}
class receiverRunnable implements Runnable {
    Socket s;
    receiverRunnable(Socket s) {
        this.s = s;
    }
    @Override
    public void run() {
        try {
            // read and log HTTP request
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter file = new BufferedWriter(new FileWriter("requestLog_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_" + s.getInetAddress().getHostAddress().replace(":", "_") + ".txt"));
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
                file.write(line);
                file.newLine();
            }
            file.flush();
            file.close();
            if(!new File("E:\\Users\\Desktop\\index.html").exists()){
                System.out.println("index does not exists");
            }
            // response with a html file
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            String htmlContent = new String(Files.readAllBytes(Paths.get("E:\\Users\\Desktop\\index.html")));
            bw.write("HTTP/1.1 200 OK");
            bw.newLine();
            bw.write("Content-Type: text/html");
            bw.newLine();
            bw.write("Content-Length: " + htmlContent.length());
            bw.newLine();
            bw.newLine();
            bw.write(htmlContent); // Write HTML content
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

## 反射

获取类的变量和方法, 以实现动态创建或者调用.

获取`Class`对象的三种方法:

1. 使用静态方法`Class.forName()`: `Class clazz = Class.forName("PACKAGE.CLASS");`
2. 调取类的字节码文件: `Class clazz = CLASS.class;`
3. 调取类的实例的`getClass()`方法: `Class clazz = new CLASS().getClass();`

获取构造方法:

|                                                              |                          |
| ------------------------------------------------------------ | ------------------------ |
| `Class: Constructor<?>[] getConstructors()`                  | 返回所有公共构造方法     |
| `Class: Constructor<?>[] getDeclaredConstructors()`          | 返回所有声明的构造方法   |
| `Class: Constructor<T> getConstructor(Class<?>... parameterTypes)` | 返回特定的公共构造方法   |
| `Class: Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes)` | 返回特定的声明的构造方法 |

创建对象: `Constructor: newInstance(Object... initargs)`

获取构造方法的参数: `Constructor: Parameter[] getParameters()`



获取变量:

|                                              |                      |
| -------------------------------------------- | -------------------- |
| `Class: Field[] getFields()`                 | 返回所有公共变量     |
| `Class: Field[] getDeclaredFields()`         | 返回所有声明的变量   |
| `Class: Field getField(String name)`         | 返回特定的公共变量   |
| `Class: Field getDeclaredField(String name)` | 返回特定的声明的变量 |

给变量赋值: `Field: void set(Object obj, Object value)`

获取变量的值: `Field: Object get(Object obj)`

获取变量的数据类型: `Field: Class<?> getType()`



获取方法:

|                                                              |                      |
| ------------------------------------------------------------ | -------------------- |
| `Class: Method[] getMethods()`                               | 返回所有公共方法     |
| `Class: Method[] getDeclaredMethods()`                       | 返回所有声明的方法   |
| `Class: Method getMethod(String name, Class<?>... parameterTypes)` | 返回特定的公共方法   |
| `Class: Method getDeclaredMethod(String name, Class<?>... parameterTypes)` | 返回特定的声明的方法 |

调用方法: `Method: Object invoke(Object obj, Object... args)`



获取权限修饰符: `Constructor/Method/Field: getModifier()`

临时取消访问限制: `Constructor/Method/Field: setAccessible(true)`

## 动态代理

可以无侵入式地给代码增加新功能. 通过创建包含要被代理的方法的接口, 使代理实现接口并调用原来的方法来实现代理.

`java.lang.reflect.Proxy: static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) ` 产生代理对象

`ClassLoader loader`: 指定类加载器

`Class<?>[] interfaces`: 指定包含需要被代理的方法的接口

```
new InvocationHandler() {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
```

指定代理对这些方法的操作, `Object proxy`为代理实例, `Method method`为被代理的方法, `Object[] args`为被代理的方法的参数

```
public class test {
    public static void main(String[] args) {
        ProxyInterface proxy = ProxyAnimal.createProxy(new Animal("dog", 10));
        System.out.println(proxy.eat());
        System.out.println(proxy.sleeping(6));
    }
}

class ProxyAnimal {
    private ProxyAnimal() {}
	// a static method to create the proxy
    public static ProxyInterface createProxy(Animal animal) {
        ProxyInterface p = (ProxyInterface) Proxy.newProxyInstance(ProxyAnimal.class.getClassLoader(), new Class[]{ProxyInterface.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // use reflection to handle methods by name and argument
                if(method.equals(ProxyInterface.class.getMethod("eat"))) {
                    System.out.println("the animal wants to eat");
                } else if (method.equals(ProxyInterface.class.getMethod("sleeping", int.class))) {
                    System.out.println("the animal wants to sleep");
                }
                return method.invoke(animal, args);
            }
        });
        return p;
    }
}

class Animal implements ProxyInterface {
    String name;
    int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String eat() {
        if (name == null) {
            System.out.println("the animal is eating");
        } else {
            System.out.println(age + " years old " + name + " is eating");
        }
        return "yumyum";
    }

    public boolean sleeping(int t) {
        if (t <= 0 || t >= 9) {
            System.out.println("unhealthy sleep time for the animal");
            return false;
        }
        if (name == null) {
            System.out.println("the animal is eating");
        } else {
            System.out.println(age + " years old " + name + " is eating");
        }
        return true;
    }
}

interface ProxyInterface {
    String eat();

    boolean sleeping(int t);
}
```