package com.gdw;

interface Component{
    void doSomething();
}
class ConcreteComponent implements Component{
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}

class Decorator implements Component{
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
class ConcreteDecorator1 extends Decorator{

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        System.out.println("功能B");
    }
}

class ConcreteDecorator2 extends Decorator{

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        System.out.println("功能C");
    }
}

class Client{
    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(
                new ConcreteDecorator1(new ConcreteComponent()));
        component.doSomething();
    }
}
