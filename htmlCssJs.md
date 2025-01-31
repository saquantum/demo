## HTML



## CSS



## JavaScript

JS中的函数:

```
function FNAME(ARG1, ARG2, ...){
	// codes...
}
```

匿名函数:

```
var FNAME = function(ARG1, ARG2, ...){
	// codes...
}

var FNAME = (ARG1, ARG2, ...) => {
	// codes...
}
```

定义对象:

```
let OBJECT = {
	ATTRIBUTE1: VALUE1,
	ATTRIBUTE2: VALUE2,
	...,
	METHODNAME: function (ARG1, ARG2, ...){
	// codes...
	}
}
```

JSON对象标记:

```
将普通的对象转化为JSON字符串: 
JSON.stringify(OBJECT);

定义JSON字符串:
let JSTR = '{"ATTRIBUTE1": VALUE1, "ATTRIBUTE2": VALUE2, ...}';

获取JSON中的对象信息:
JSON.parse(JSTR).ATTRIBUTE;
```

DOM操作:

​	获取html标签:

```
document.getElementById("ID"); // 返回匹配到的第一个标签
document.getElementsByClassName("CLASSNAME"); // 返回所有匹配到的标签的数组
document.getElementsByTagName("TAG"); // 返回所有配到的标签的数组
document.querySelector("selector") // 返回匹配到的第一个标签
document.querySelectorAll("selector") // 返回所有匹配到的标签的数组

selector: TAG, .CLASS, #ID
```

​	操作html标签:

``` 
document.querySelector(".title").innerText = "..." // 修改标签内的文本
document.querySelector(".title").textContent = "..." // 修改标签内的文本

document.querySelector("div").innerHTML = "<span class="new">text</span>"; // 替换成新的html标签

document.querySelector("p").style.color = red;
document.querySelector("p").style.fontSize = 20pt; // 修改CSS属性


document.querySelector("a").setAttribute("href", "https://www.google.com");
document.querySelector("a").setAttribute("target", "_blank");  // 修改标签属性


document.querySelector("#text").classList.add("highlight");  // 给标签添加一个类名
document.querySelector("#text").classList.remove("highlight");  // 删除类名
document.querySelector("#text").classList.toggle("hidden");  // 若存在类名则删除, 不存在则添加

let newDiv = document.createElement("div");
newDiv.innerText = "I am a new div!";
document.body.appendChild(newDiv); // 创建新标签并插入到<body>中的末尾

let newChild = document.createElement("p");
newChild.innerText = "New child paragraph!";
document.querySelector("#parent");.appendChild(newChild); // 创建新标签并插入到指定标签中的末尾

let unwanted = document.getElementById("remove-me");
unwanted.remove(); // 删除标签
```

事件监听:

|          |                                                              |
| -------- | ------------------------------------------------------------ |
| 鼠标     | `click`, `dblclick`, `mousedown`, `mouseup`, `mousemove`, `mouseenter`, `mouseleave` |
| 键盘     | `keydown`, `keyup`                                           |
| 表格     | `submit`, `change`, `input`, `focus`, `blur`                 |
| 窗口     | `load`, `resize`, `scroll`, `unload`                         |
| 手机触屏 | `touchstart`, `touchmove`, `touchend`                        |
| 剪切板   | `copy`, `cut`, `paste`                                       |

```
document.querySelector("...").addEventListener("click", () => {alert("clicked");})
```



## Vue

导入Vue:

```
<head>
    <script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
</head>
```

常用指令:

​	1. `v-for` 遍历列表

```
<TAG v-for="(item, index) in items" :key="item.id">{{item}}</TAG> // 完整语法
<TAG v-for="item in items"> // 简化版语法
```

`items`被遍历的数组, `item`遍历得到的元素, `key`元素的唯一标识

```
<body>
    <div id="app">
        <ul>
            <li v-for="(fruit, index) in fruits" :key="JSON.parse(fruit).count">{{JSON.parse(fruit).name}} = {{JSON.parse(fruit).count}}</li>
        </ul>
    </div>
    
    <script>
        Vue.createApp({
            data() {
                return {
                    fruits: ['{"name": "apple", "count": 10}', '{"name": "banana", "count": 2}']
                };
            }
        }).mount("#app");
    </script>
</body>
```

	2. `v-bind` 给标签绑定属性

```
<TAG v-bind:ATTRIBUTE="VALUE"> // 完整语法
<TAG :ATTRIBUTE="VALUE"> // 简化语法
<TAG v-bind="ATTRIBUTES"> // 一次绑定多个属性
```

绑定单个属性:

```
<div id="app">
    <a :href="website" target="_blank">Visit Google</a>
</div>

<script>
    Vue.createApp({
        data() {
            return { website: "https://www.google.com" };
        }
    }).mount("#app");
</script>
```

绑定多个属性:

```
<div id="app">
    <a v-bind="linkAttributes">Click Me</a>
</div>

<script>
    Vue.createApp({
        data() {
            return {
                linkAttributes: {
                    href: "https://www.vuejs.org",
                    target: "_blank",
                    title: "Go to Vue.js"
                }
            };
        }
    }).mount("#app");
</script>

```

