# rocket
我们在实际项目中会遇到这样一种场景：一个job从数据库中获取一批数据，这批数据通常放在一个List集合中，
我们遍历此集合逐个进行各自的业务处理操作。如果这批数据很少，或者每个记录处理耗时很低，这当然没什么问题，
但是如果这批数据很多，并且每条记录又非常耗时，那么还是这种单线程处理模式将会非常耗时，为了解决这个问题，
我们可以使用多线程处理这一个列表。rocket使用线程池封装了具体实现细节，用户只需要很简单的两步即可完成接入，
具体如何操作，请看如下例子。
##rocket-example
###step1
实现接口TaskService
```
@Service
@Slf4j
public class MessageTaskService implements TaskService<Message> {

    @Autowired
    private MessageService messageService;

    @Override
    @SneakyThrows(InterruptedException.class)
    public void execTask(Message message) {
        TimeUnit.MICROSECONDS.sleep(new Random().nextInt(1000));
        /**
         * 调用具体业务处理service
         */
        this.messageService.send(message);
    }
}
```
###step2
在具体调用处注入TaskExecutorService以及step1中的实现类
```
@Component
@EnableScheduling
public class TaskExample {
    @Autowired
    private TaskExecutorService taskExecutorService;
    @Autowired
    private MessageTaskService messageTaskService;

    @Scheduled(cron = "0/5 * * * * ?")
    void send() {
        /**
         * 模拟从数据库取出100000条数据操作
         */
        int size = 100000;
        List<Message> messages = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            messages.add(Message.builder().id(i).body("message " + i).build());
        }
        /**
         * 使用多线程同时处理这100000条数据
         */
        this.taskExecutorService.executeTask("sendMessageTask", messageTaskService, messages);
    }
}
```