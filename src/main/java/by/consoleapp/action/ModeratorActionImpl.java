package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.entity.Moderator;
import by.entity.Order;
import by.service.OrderService;

public class ModeratorActionImpl implements ModeratorAction {
    private Writer writer;
    private Reader reader;
    private OrderService orderService;

    public ModeratorActionImpl(Writer writer, Reader reader, OrderService orderService) {
        this.writer = writer;
        this.reader = reader;
        this.orderService = orderService;
    }

    public void changeOrderStatus() {
        Moderator moderator = (Moderator)ConsoleApplicationImpl.session.getUser();
        Order[] allByStore = orderService.findAllByStore(moderator.getStore());
        writer.write("Выберите заказ, статус которого хотите изменить");
        if (allByStore.length == 0) {
            writer.write("Зарегистрированных заказов нет. Операция отменена.");
        } else {
            int i;
            for(i = 0; i < allByStore.length; ++i) {
                writer.write(i + " " + allByStore[i].getStore());
            }
            i = reader.readNumber();
            Order order = allByStore[i];
            writer.write("1 - Обрабатывается, 2 - Закрыт");
            int ii = reader.readNumber();
            if (ii == 1) {
                order.setStatus(Order.Status.PROCESSED);
            } else if (ii == 2) {
                order.setStatus(Order.Status.CLOSE);
                order.getUser().setOrdered(false);
            } else {
                writer.write("Такого статус нет!");
            }
        }
    }

    public void deleteOrder() {
        Moderator moder = (Moderator)ConsoleApplicationImpl.session.getUser();
        Order[] allByStore = orderService.findAllByStore(moder.getStore());
        writer.write("Выберите заказ, который хотите удалить");
        int or;
        for(or = 0; or < allByStore.length; ++or) {
            writer.write(or + " " + allByStore[or].getAddress() + " " + allByStore[or].getUser().getEmail());
        }
        or = reader.readNumber();
        int id = allByStore[or].getId();
        orderService.delete(id);
        writer.write("Заказ удален");
    }
}
