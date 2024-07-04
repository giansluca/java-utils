package org.gmdev;

import org.gmdev.concurrency.*;
import org.gmdev.event.eventsourcing.ServiceManager;
import org.gmdev.event.fileservice.FileServiceManager;
import org.gmdev.event.messageservice.MessageServiceManager;
import org.gmdev.event.producerconsumer.ClassicProducerConsumer;
import org.gmdev.event.producerconsumer.ProducerConsumerWithBlockingQueue;
import org.gmdev.functional.callback._Callback;
import org.gmdev.functional.callback._Lambdas;
import org.gmdev.functional.combinatorpattern._Combinator;
import org.gmdev.functional.functionalinterface._Consumer;
import org.gmdev.functional.functionalinterface._Function;
import org.gmdev.functional.functionalinterface._Predicate;
import org.gmdev.functional.functionalinterface._Supplier;
import org.gmdev.functional.optionals._Optional;
import org.gmdev.functional.streams._Stream;
import org.gmdev.security.AESEncryption;
import org.gmdev.security.SimpleEncryption;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Ready");

        // ---- FUNCTIONAL ----

//        _Stream.driver();
//        _Function.driver();
//        _Consumer.driver();
//        _Predicate.driver();
//        _Supplier.driver();
//        _Optional.driver();
//        _Combinator.driver();
//        _Callback.driver();
//        _Lambdas.driver();

        // ---- CONCURRENCY ----

        // Simple Threads
//        RunClass.threadExample();
//        RunClass.runnableExample();
//        RunClass.runnableExampleAnonymous();
//        RunClass.runnableExampleAnonymousCompact();
//        RunClass.RunnableExampleLambdaExpression();
//        RunClass.threadSleepExample();
//        RunClass.threadJoinExample();
        // Executor and Thread pool
//        ExecutorClass.executorsExampleSingle();
//        ExecutorClass.executorsExampleMulti();
//        ExecutorClass.scheduledExecutorsExample();
//        ExecutorClass.ScheduledExecutorsPeriodicExample();
        // Callable and Future
//        CallableFutureClass.futureAndCallableExample();
//        CallableFutureClass.futureIsDoneExample();
//        CallableFutureClass.futureCancelExample();
//        CallableFutureClass.invokeAllExample();
//        CallableFutureClass.invokeAnyExample();
        // Synchronization
//        Synchronization.raceConditionExample();
//        Synchronization.memoryConsistencyErrorExample();
//        Synchronization.synchronizedMethodExample();
//        Synchronization.volatileKeywordExample();
//        Synchronization.reentrantLocExample();
//        Synchronization.reentrantLockMethodsCounter();
//        Synchronization.reentrantReadWriteLock();
//        Synchronization.atomicIntegerExample();
        // Completable Future
//        CompletableFutureExample_1.simpleCompletableFuture();
//        CompletableFutureExample_1.simpleCompletableFutureLambda();
//        CompletableFutureExample_1.returnCompletableFuture();
//        CompletableFutureExample_1.returnCompletableFutureLambda();
//        CompletableFutureExample_1.poolCompletableFuture();
//        CompletableFutureExample_1.thenApplyCompletableFuture();
//        CompletableFutureExample_1.thenApplyChainCompletableFuture();
//        CompletableFutureExample_1.thenAcceptCompletableFuture();
//        CompletableFutureExample_1.thenRunCompletableFuture();
//        CompletableFutureExample_1.thenApplyAsyncCompletableFuture();
//        CompletableFutureExample_2.thenComposeCompletableFuture();
//        CompletableFutureExample_2.thenCombineCompletableFuture();
//        CompletableFutureExample_2.exceptionCompletableFuture();

        // ---- EVENTS ----

        // Event source
//        FileServiceManager eventSourceManager = new FileServiceManager();
//        eventSourceManager.startService();
        // Producer Consumer
//        ClassicProducerConsumer classicProducerConsumer = new ClassicProducerConsumer();
//        classicProducerConsumer.start();
//        ProducerConsumerWithBlockingQueue producerConsumerBlockingQueue = new ProducerConsumerWithBlockingQueue();
//        producerConsumerBlockingQueue.startNormalThread();
//        producerConsumerBlockingQueue.startServiceExecutor();
        // Message service
//        MessageServiceManager messageService = new MessageServiceManager();
//        messageService.start();
        // File service
//        FileServiceManager eventsProducer = new FileServiceManager();
//        eventsProducer.start();

        // ---- ENCRYPTION ----

        // Simple encryption
//        SimpleEncryption.test();
        // Encrypt - Decrypt 128 bit AES
//        String encPassword = AESEncryption.encodeBase64AES("1234567");
//        System.out.println(encPassword);
//        System.out.println(AESEncryption.decodeBase64AES(encPassword));
    }

}
