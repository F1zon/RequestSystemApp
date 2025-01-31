package ru.example.requestSystem.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.requestSystem.db.RequestModel;
import ru.example.requestSystem.db.dao.Request;
import ru.example.requestSystem.db.dao.User;
import ru.example.requestSystem.db.dto.RequestDto;
import ru.example.requestSystem.db.dto.UserDto;
import ru.example.requestSystem.db.repository.RequestRepo;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.button.Button;
import ru.example.requestSystem.db.repository.UserRepo;
import ru.example.requestSystem.service.RequestServiceImp;
import ru.example.requestSystem.service.UserServiceImp;

import java.util.ArrayList;
import java.util.List;

@Route("requests")
@PageTitle("Requests")
public class RequestList extends AppLayout implements HasUrlParameter<Long> {
    VerticalLayout verticalLayout;
    Grid<RequestModel> grid;
    RouterLink routerLink;
    UserDto user;
    Long id;

    @Autowired
    RequestServiceImp requestService;

    @Autowired
    UserServiceImp userService;

    public RequestList() {
        verticalLayout = new VerticalLayout();
        grid = new Grid<>();
        routerLink = new RouterLink("Создать заявку", ManageRequest.class, 0L);
        verticalLayout.add(routerLink);
        verticalLayout.add(grid);
        addToNavbar(new H3("Список заявок"));
        setContent(verticalLayout);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        this.id = id;
        this.user = userService.findById(id);
    }

    @PostConstruct
    public void fillGrid() {
        List<RequestModel> requests = getAllRequests();

        if (!requests.isEmpty()) {
            // Вывод столбцов
            grid.addColumn(RequestModel::getClientId).setHeader("Заказчик");
            grid.addColumn(RequestModel::getOperatorId).setHeader("Исполнитель");
            grid.addColumn(RequestModel::getData).setHeader("Описание");
            grid.addColumn(RequestModel::getCreatedAt).setHeader("Созданно");
            grid.addColumn(RequestModel::getUpdatedAt).setHeader("Обновленно");

            // Кнопка удаления и ректирования
            grid.addColumn(new NativeButtonRenderer<RequestModel>("Редактировать", request -> {
                UI.getCurrent().navigate(ManageRequest.class, request.getId());
            }));

            if (user.getRole().equals("operator")) {
                grid.addColumn(new NativeButtonRenderer<RequestModel>("Удалить", request -> {
                    Dialog dialog = new Dialog();
                    Button confirm = new Button("Удалить");
                    Button cancel = new Button("Отмена");
                    dialog.add("Удалить заявку?");
                    dialog.add(confirm);
                    dialog.add(cancel);
                    confirm.addClickListener(e -> {
                        requestService.deleteById(request.getId());
                        dialog.close();
                        Notification notification = new Notification("Заявка удалена", 1000);
                        notification.setPosition(Notification.Position.MIDDLE);
                        notification.open();
                        grid.setItems(getAllRequests());
                    });

                    cancel.addClickListener(e -> {
                        dialog.close();
                    });
                    dialog.open();
                }));
            }

            grid.setItems(requests);
        }
    }

    private List<RequestModel> getAllRequests() {
        List<RequestDto> tmp = requestService.findAll();
        List<RequestModel> result = new ArrayList<>();

        if (user.getRole().equals("client")) {
            for (RequestDto request : tmp) {
                if (request.getClientId().equals(user.getId())) {
                    result.add(new RequestModel(request.getId(), userService.findById(request.getClientId()).getName(),
                            userService.findById(request.getOperatorId()).getName(), request.getStatus(),
                            request.getData(), request.getComment(), request.getCreatedAt(), request.getUpdatedAt()));
                }
            }
        } else {
            for (RequestDto request : tmp) {
                result.add(new RequestModel(request.getId(), userService.findById(request.getClientId()).getName(),
                        userService.findById(request.getOperatorId()).getName(), request.getStatus(),
                        request.getData(), request.getComment(), request.getCreatedAt(), request.getUpdatedAt()));
            }
        }

        return result;
    }
}
