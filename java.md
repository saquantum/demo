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

字符缓冲流: `BufferedReader(Reader r)`, `BufferedWriter(Writer r)` . 用法与基本流一致, 但二者的默认缓冲区长度是8192个字符(8192*2个字节). 要修改缓冲区长度, 使用构造函数的第二个参数. 两个独有方法: 字符缓冲输入流的`String readLine()`一次读取一整行数据(但不会读取换行符). 读取到文件末尾的返回值不是`-1`而是`null`. 字符缓冲输入流的`void newLine()`输出换行符.



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

死锁: 两个锁相互嵌套, 导致程序卡死.



等待唤醒机制:



继承于`Object`的方法:

|                    |                                    |
| ------------------ | ---------------------------------- |
| `void wait()`      | 让当前线程等待, 直到被其他线程唤醒 |
| `void notify()`    | 随机唤醒一个线程                   |
| `void notifyAll()` | 唤醒所有线程                       |



线程的6种状态

|                          |                    |
| ------------------------ | ------------------ |
| 新建 `new`               | 创建线程对象       |
| 就绪 `runnable`          | `start()`          |
| 被阻塞 `blocked`         | 无法获得`Lock`对象 |
| 等待 `waiting`           | `wait()`           |
| 计时等待 `timed waiting` | `sleep()`          |
| 结束 `terminated`        | 运行完毕           |

