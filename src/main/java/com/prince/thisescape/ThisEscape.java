package com.prince.thisescape;

/**
 * 引用逃逸:
 * 指对象还没有构造完成，它的this引用就被发布出去了
 * 这是危及到线程安全的，因为其他线程有可能通过这个逸出的引用访问到“初始化了一半”的对象
 * <p>
 * 内部类、匿名内部类都可以访问外部类的对象的域，为什么会这样，
 * 实际上是因为内部类构造的时候，会把外部类的对象this隐式的作为一个参数传递给内部类的构造方法，这个工作是编译器做的
 * <p>
 * ThisEscape在构造函数中引入了一个内部类ConfigurationListener，而内部类会自动的持有其外部类（这里是ThisEscape）的this引用。
 * source.registerListener会将内部类发布出去，从而ThisEscape.this引用也随着内部类被发布了出去。
 *
 * 在一个类的构造器创建了一个内部类（内部类本身是拥有对外部类的所有成员的访问权的），此时外部类的成员变量还没初始化完成。
 * 但是，同时这个内部类被其他线程获取到，并且调用了内部类可以访问到外部类还没来得及初始化的成员变量的方法。
 */
//public class ThisEscape {
//    public final int id;
//    public final String name;
//
//    public ThisEscape(EventSource<EventListener> source) {
//        id = 1;
//        source.registerListener(new EventListener() {  //内部类是可以直接访问外部类的成员变量的（外部类引用this被内部类获取了）
//            public void onEvent(Object obj) {
//                System.out.println("id: " + ThisEscape.this.id);
//                System.out.println("name: " + ThisEscape.this.name);
//            }
//        });
//        name = "flysqrlboy";
//    }
//}