package ru.example.requestSystem.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.requestSystem.db.dao.Request;
import ru.example.requestSystem.db.repository.RequestRepo;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.button.Button;

import java.util.List;

@Route("requests")
public class RequestList extends AppLayout {
    VerticalLayout verticalLayout;
    Grid<Request> grid;
    RouterLink routerLink;

    @Autowired
    RequestRepo requestRepo;

    public RequestList() {
        verticalLayout = new VerticalLayout();
        grid = new Grid<>();
        routerLink = new RouterLink("Создать заявку", ManageRequest.class, 0L);
        verticalLayout.add(routerLink);
        verticalLayout.add(grid);
        addToNavbar(new H3("Список заявок"));
        setContent(verticalLayout);
    }

    @PostConstruct
    public void fillGrid() {
        List<Request> requests = requestRepo.findAll();

        if (!requests.isEmpty()) {
            // Вывод столбцов
            grid.addColumn(Request::getClientId).setHeader("Заказчик");
            grid.addColumn(Request::getOperatorId).setHeader("Исполнитель");
            grid.addColumn(Request::getData).setHeader("Описание");
            grid.addColumn(Request::getCreatedAt).setHeader("Созданно");
            grid.addColumn(Request::getUpdatedAt).setHeader("Обновленно");

            // Кнопка удаления и ректирования
            grid.addColumn(new NativeButtonRenderer<Request>("Редактировать", request -> {
                UI.getCurrent().navigate(ManageRequest.class, request.getId());
            }));

            grid.addColumn(new NativeButtonRenderer<Request>("Удалить", request -> {
                Dialog dialog = new Dialog();
                Button confirm = new Button("Удалить");
                Button cancel = new Button("Отмена");
                dialog.add("Удалить заявку?");
                dialog.add(confirm);
                dialog.add(cancel);
                confirm.addClickListener(e -> {
                    requestRepo.delete(request);
                    dialog.close();
                    Notification notification = new Notification("Заявка удалена", 1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                    grid.setItems(requestRepo.findAll());
                });

                cancel.addClickListener(e -> {
                    dialog.close();
                });
                dialog.open();
            }));

            grid.setItems(requests);
        }
    }
}
