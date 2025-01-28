package ru.example.requestSystem.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.requestSystem.db.dao.Request;
import ru.example.requestSystem.db.repository.RequestRepo;

import java.util.Optional;

@Route("home")
public class ManageRequest extends AppLayout implements HasUrlParameter<Long> {

    Long id;
    FormLayout form;
    TextField data;
    Button save;

    @Autowired
    RequestRepo requestRepo;

    public ManageRequest() {
        // Объекты для формы
        form = new FormLayout();
        data = new TextField("Описание");
        save = new Button("Сохранить");

        // Добавление элементов на форму
        form.add(data, save);
        setContent(form);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long requestId) {
        id = requestId;
        if (!id.equals(0L)) {
            addToNavbar(new H3("Редактирование заявки"));
        } else {
            addToNavbar(new H3("Создание заявки"));
        }

        fillForm();
    }

    public void fillForm() {
        if (!id.equals(0L)) {
            Optional<Request> request = requestRepo.findById(id);
            request.ifPresent(request1 -> {
                data.setValue(request1.getData());
            });
        }

        save.addAttachListener(e -> {
           // Создание объекта заявки по полученному значению с формы
           Request req = new Request();

           if (id.equals(0L)) {
               req.setId(id);
           }

           req.setData(data.getValue());
           requestRepo.save(req);

            Notification notification = new Notification(id.equals(0L)
                    ? "Заявка созданна"
                    : "Заявка изменена", 1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(RequestList.class);
            });
            form.setEnabled(false);
            notification.open();
        });
    }
}
