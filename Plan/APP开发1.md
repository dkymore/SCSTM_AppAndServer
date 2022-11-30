## 安卓开发

### 需求确定

> 首页模块：
>
> 1) 轮播图：顶部呈现。
> 2) 功能区：科技馆介绍、开闭馆时间、参观攻略、在线预约。点击每个功能，跳转到具体详情介绍。
> 3) 在线预约：选择预约日期、人数、上午/下午等，显示当前时间段剩余预约量。
> 4) 新闻列表（最新3条），点击跳转到新闻列表页面（全部）。
> 5) 新闻详情：点击每条新闻，显示基本信息。
> 6) 以上内容来源参考官方网站。
>    展品精粹模块：
>    1) 显示展品类别，至少6类。
>    2) 点击某个类别，显示其包含的所有展品（至少4个），点击某个展品显示其基本介绍：包括名称、内容、图片、（评论功能可选）
>    3) 以上内容来源参考官方网站。
>
> 个人中心模块：
>
> 1) 用户登录与注册：根据昵称和密码实现登录。
> 2) 用户注册信息：包括但不限于昵称、姓名、性别、电话、职业、兴趣（多项）。
> 3) 用户信息修改。
> 4) 用户预约记录。



 ### UI设计

页面全部使用fragment

主Activity内放置

- HeadBar 头部框
- Fragment Content 页面内容
- SideBar 侧边栏



### 代码框架设计

#### UI类

- View


  - HeaderView.java


  - SliderNavView.java

- Fragment

  - MainFragment.java
  - LoginAndLogupFragment.java


  - NewslistDetailFragment.java


  - NewslistFragment.java


  - ReserveFragment.java


  - ShowupDetailFragment.java


  - ShowupFragment.java


  - ShowupInnerFragment.java


  - WalkthroughFragment.java


#### UI框架

考虑到有大量的fragment 

需要一个fragment管理类 

以及 为方便批量添加功能 创建一个 MyFragment 类继承 fragment 

其他fragment页面继承自 myfragment

详情见下



#### 数据处理

- DataProvider

提供本地json读取的数据 并缓存结果

- LocalDB

本地存储 因为项目较小 无需使用 本地 sqlite

暂时使用 SharedPreferences

- RequestHelper 

构建各种网络请求并初步解析返回值

初步处理返回的code值 详见后端返回的请求

- Player

处理本地用户 如用户Token 及对用户相关的网络请求进一步封装



### 代码设计

#### UI框架

- MyFragment

```java
    /**
     * 返回类的ID 用于在 pagemanager 识别类
     * @return
     */
    public abstract String getID();

    /**
     * 用于在 设置在 headerview 中显示的字符串
     * @return
     */
    public String getHeader(){return "";}

    /**
     * 是否添加到历史记录
     * @return
     */
    public boolean isAddTohistory(){return false;}

    /**
     * 实际继承的 onCreate
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onMyCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onMyCreateView(inflater,container,savedInstanceState);
    }

    // 添加的 behaviour 
    // 当 pagemanger 切换时 调用对应方法
    // 方便处理数据
    // Update 方法 在 MainActiviy 中以线程调用 默认每3秒调用一次
    
    public void Awake(){}

    public void Start(){}

    public void Start(String intent){Start();}

    public void Update(){}

```

- PageManager

```java
// 回调函数
    public interface PageManagerCallback{
        void invoke();
    }

    // 一些变量
    MyFragment currFragment;
    FragmentContainerView fcv;
    FragmentManager manager;
    FragmentTransaction transaction;

    // 通过这变量确认
    // 因为一次最多只会有一个在切换 就无需使用时间戳
    /**
     * 是否在切换中
     */
    boolean isChangeing = false;

    // 存储所有创建的 myfragment
    List<MyFragment> fragments = new ArrayList<>();

    // 返回值类及栈用来保存返回值
    public class History{
        public String name;
        public String Intent;

        public History(String name, String intent) {
            this.name = name;
            Intent = intent;
        }
    }
    Stack<History> history =  new Stack<>();

    // 单例模式
    public static final PageManager instance = new PageManager();


    public void Init(MainActivity main){
        fcv = main.findViewById(R.id.nav_host_fragment_content_main);
        manager = main.getSupportFragmentManager();
        DataInit();
    }

    // 一次性创建
    // 其实没有必要 可以在切换时才创建 更节约性能 减少启动时间
    void DataInit(){
        history.clear();
        fragments.clear();
        fragments.add(new LoginAndLogupFragment());
        fragments.add(new MainFragment());
        fragments.add(new NewslistFragment());
        fragments.add(new NewslistDetailFragment());
        fragments.add(new ReserveFragment());
        fragments.add(new ShowupFragment());
        fragments.add(new ShowupInnerFragment());
        fragments.add(new ShowupDetailFragment());
        fragments.add(new WalkthroughFragment());
    }

    public void changeToPage(String To){
        changeToPage(To,()->{});
    }

    public void changeToPage(String To,PageManagerCallback callback){
        String From = ((MyFragment) currFragment).getID();
        changeToPage(From,To,callback,"");
    }

    public void changeToPage(String To,String intent){
        String From = ((MyFragment) currFragment).getID();
        changeToPage(From,To,()->{},intent);
    }

    // 切换页面主力方法
    // 使用 transaction 的 hide 和 show
    public void changeToPage(String From,String To,PageManagerCallback callback,String intent){
        if(From.isEmpty()||To.isEmpty()){Log.e("changeToPage","Args Empty");return;}
        if(isChangeing){Log.w("changeToPage","is Changing");return;}
        isChangeing = true;

        OnBeforeChangePage(From,To);

        transaction = manager.beginTransaction();
        // 可以把动画也抽离 作为一个参数
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.hide(currFragment);
        MyFragment toFragement = getFragment(To);
        if(toFragement.isAdded()){
            transaction.show(toFragement);
        }else{
            transaction.add(R.id.nav_host_fragment_content_main,toFragement);
        }

        transaction.runOnCommit(()->{
            isChangeing = false;
            currFragment = toFragement;
            if(currFragment.isAddTohistory()){history.push(new History(To,intent));}
            OnAfterChangePage(From,To);

            callback.invoke();
            currFragment.Start(intent);
        }).commit();
    }

    // 这里方便期间就写死了启动"Main" 可以设置为参数
    // 也可以和上面的 changeToPage 合并
    public void startToPage(){
        MyFragment toFragement = getFragment("Main");
        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(R.id.nav_host_fragment_content_main,toFragement);
        transaction.runOnCommit(()->{
            currFragment = toFragement;
            if(currFragment.isAddTohistory()){history.push(new History("Main",""));}

            OnAfterChangePage("","Main");
        }).commit();
    }


    public MyFragment getCurrFragment(){
        return currFragment;
    }

    public MyFragment getFragment(String id){
        for (MyFragment e: fragments
             ) {
            if(e.getID().equals(id)){
                return e;
            }
        }
        return null;
    }

    // before 和 after change 的方法
    // 可以通过判断 From 和 To 进行一些操作
    private void OnBeforeChangePage(String From,String To){
        SliderNavView.instance.Close();
    }

    private void OnAfterChangePage(String From,String To){
        if(To.equals("Main")){
            HeaderView.instance.shortHeader();
        }else{
            HeaderView.instance.longHeader(currFragment.getHeader());
        }
    }

    // 当返回时 去返回值切换页面
    // 由 MainAciticy 调用
    public boolean OnBack(){
        if(history.size()>1){
            history.pop();
            History h = history.pop();
            changeToPage(h.name,h.Intent);
            return true;
        }
        return false;
    }
```

#### 侧边栏

##### 个人中心

switch type 更改UI

显示预约的内容



#### 顶部栏

##### logo 文字 

根据页面切换顶部LOGO位置



#### 首页

详情见代码文件

##### 轮播图

使用viewpage2

在Update 中 `mainBanner.setCurrentItem((mainBanner.getCurrentItem()+1)%3); `实现轮播

##### 主要按钮

图片按钮

##### 开闭馆时间

由 Update 更新时间 在`SCSTMTimeManager`中获取开闭馆状态并显示

##### 新闻

list实现



####  展览页

使用3个页面 根据数据动态生成内容

UI部分详见 `TableBuilder` 



#### 登录及注册

根据用户名判断是否注册在进一步要求信息

UI chooser 详见 `chooser2`



#### 参观攻略

纯图片



#### 新闻

同 展览页 



#### 注册页

选择组件参考 `choose2` `choose5`

时间生成参考 `SCSTMTimeManager`
