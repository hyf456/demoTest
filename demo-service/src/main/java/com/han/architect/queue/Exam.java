package com.han.architect.queue;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hanyf
 * @Description: 多考生考试
 * 模拟一个考试的日子，考试时间为120分钟，30分钟后才可交卷，当时间到了，或者学生都交完卷了宣布考试结束。
 *
 * 实现思想：用DelayQueue存储考生（Student类），每一个考生都有自己的名字和完成试卷的时间，Teacher线程对DelayQueue进行监控，**收取完成试卷小于120分钟的学生的试卷。**当考试时间120分钟到时，先关闭Teacher线程，然后强制DelayQueue中还存在的考生交卷。每一个考生交卷都会进行一次
 * @Date: 2020/6/17 18:43
 */
public class Exam {

    public static void main(String[] args) throws InterruptedException {
        int studentNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(studentNumber + 1);
        DelayQueue<Student> students = new DelayQueue<>();
        Random random = new Random();
        for (int i = 0; i < studentNumber; i++) {
            students.put(new Student("student" + (i + 1), random.nextInt(120) + 30, countDownLatch));
        }

        //老师监考  准备一个老师即可  把学生们需要交给老师 以便监控
        Thread teacherThread = new Thread(new Teacher(students));

        //强制交卷 时间为120分钟  并且把老师线程穿进去收卷纸
        students.put(new EndExam(students, 120, countDownLatch, teacherThread));
        teacherThread.start();

        countDownLatch.await();
        System.out.println(" 考试时间到，全部交卷！");
    }

}

class Student implements Runnable, Delayed {

    private String name;
    private long workTime; //希望用时  有的学生小于120分钟  有的会大于120分钟 会被强制交卷
    private long submitTime; //交卷的时间
    private boolean isForce = false;
    private CountDownLatch countDownLatch;

    public Student() {
    }

    public Student(String name, long workTime, CountDownLatch countDownLatch) {
        this.name = name;
        this.workTime = workTime;
        this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS) + System.nanoTime();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == null || !(o instanceof Student))
            return 1;
        if (o == this)
            return 0;
        Student s = (Student) o;
        if (this.workTime > s.workTime) {
            return 1;
        } else if (this.workTime == s.workTime) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public void run() {
        if (isForce) {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟" + " ,实际用时 120分钟");
        } else {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟" + " ,实际用时 " + workTime + " 分钟");
        }
        countDownLatch.countDown(); //交卷一个 减一个人
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean isForce) {
        this.isForce = isForce;
    }

}

class EndExam extends Student {

    private DelayQueue<Student> students;
    private CountDownLatch countDownLatch;
    private Thread teacherThread;

    public EndExam(DelayQueue<Student> students, long workTime, CountDownLatch countDownLatch, Thread teacherThread) {
        super("强制收卷", workTime, countDownLatch);
        this.students = students;
        this.countDownLatch = countDownLatch;
        this.teacherThread = teacherThread;
    }


    @Override
    public void run() {
        teacherThread.interrupt(); //打断线程 强制交卷  不要让学生自己take了 也可采用一个全局变量标记
        Student tmpStudent;

        //遍历所有还未执行的学生们 把他们拿出来 手动调用他们的run方法交卷
        for (Iterator<Student> iterator2 = students.iterator(); iterator2.hasNext(); ) {
            tmpStudent = iterator2.next();
            tmpStudent.setForce(true);
            tmpStudent.run();
        }
        countDownLatch.countDown(); //最后注意 把自己强制交卷的线程 也要一下
    }

}

class Teacher implements Runnable {

    // 老师需要知道  自己监控哪些学生 宣布考试开始
    private DelayQueue<Student> students;

    public Teacher(DelayQueue<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        try {
            System.out.println(" test start");
            //宣布考试后 才能让学生开始run
            // 此处需要注意 take是阻塞的 只要时间到了  才会take出来 才会执行run方法
            // 中途有可能线程会被interrupted（比如强制交卷的情况下 就不能再让take了 需要执行强制交卷的线程任务）
            while (!Thread.interrupted()) {
                students.take().run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
/*输出：
 test start
student9 交卷, 希望用时35分钟 ,实际用时 35 分钟
student5 交卷, 希望用时45分钟 ,实际用时 45 分钟
student6 交卷, 希望用时45分钟 ,实际用时 45 分钟
student20 交卷, 希望用时52分钟 ,实际用时 52 分钟
student11 交卷, 希望用时62分钟 ,实际用时 62 分钟
student2 交卷, 希望用时79分钟 ,实际用时 79 分钟
student15 交卷, 希望用时81分钟 ,实际用时 81 分钟
student8 交卷, 希望用时83分钟 ,实际用时 83 分钟
student10 交卷, 希望用时83分钟 ,实际用时 83 分钟
student1 交卷, 希望用时83分钟 ,实际用时 83 分钟
student18 交卷, 希望用时94分钟 ,实际用时 94 分钟
student3 交卷, 希望用时94分钟 ,实际用时 94 分钟
student16 交卷, 希望用时98分钟 ,实际用时 98 分钟
student7 交卷, 希望用时114分钟 ,实际用时 114 分钟
student12 交卷, 希望用时118分钟 ,实际用时 118 分钟
student19 交卷, 希望用时122分钟 ,实际用时 120分钟
student17 交卷, 希望用时125分钟 ,实际用时 120分钟
student14 交卷, 希望用时134分钟 ,实际用时 120分钟
student4 交卷, 希望用时148分钟 ,实际用时 120分钟
student13 交卷, 希望用时143分钟 ,实际用时 120分钟
 考试时间到，全部交卷！*/
