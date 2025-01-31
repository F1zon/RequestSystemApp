package ru.example.requestSystem.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.requestSystem.db.dao.Request;
import ru.example.requestSystem.db.dto.RequestDto;
import ru.example.requestSystem.db.enums.Status;
import ru.example.requestSystem.db.enums.StatusConverter;
import ru.example.requestSystem.db.repository.RequestRepo;
import ru.example.requestSystem.service.RequestServiceImp;

import java.util.List;
import java.util.Optional;

@Route("home")
public class ManageRequest extends AppLayout implements HasUrlParameter<Long> {

    Long id;
    FormLayout form = new FormLayout();
    TextField data = new TextField("Описание");
    TextField comment = new TextField("Комментарий");
    ComboBox<Status> statusComboBox = new ComboBox<Status>("Статус");

    Button save = new Button("Сохранить");
    Button cancel = new Button("Cancel");

    @Autowired
    RequestServiceImp requestService;

    public ManageRequest() {
        addClassName("manage-request");

        statusComboBox.setItems(Status.values());

        // Добавление элементов на форму
        form.add(data, comment, statusComboBox, createButtonsLayout());
        setContent(form);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, cancel);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long requestId) {
        id = requestId;

        if (requestService.findById(id) == null) {
            addToNavbar(new H3("Создание заявки"));
        } else {
            addToNavbar(new H3("Редактирование заявки"));
        }

        fillForm();
    }

    public void fillForm() {
        if (requestService.findById(id) != null) {
            StatusConverter converter = new StatusConverter();
            RequestDto request = requestService.findById(id);
            data.setValue(request.getData());
            comment.setValue(request.getComment() == null ? "" : request.getComment());
            statusComboBox.setValue(converter.convertToEntityAttribute(request.getStatus()));
        }

        save.addAttachListener(e -> {
           // Создание объекта заявки по полученному значению с формы
            RequestDto req = new RequestDto();
            Notification notification = id.equals(0L) ? createRequest() : editRequest();

//            requestRepo.save(req);

            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(RequestList.class);
            });
            form.setEnabled(false);
            notification.open();
        });
    }

    private Notification createRequest() {
        Request request = new Request();
        request.setData(data.getValue());
//        requestRepo.save(request);
        return new Notification("Заявка созданна", 1000);
    }

    private Notification editRequest() {
        RequestDto request = requestService.findById(id);
        request.setData(data.getValue());

        return new Notification("Заявка изменена", 1000);
    }
}
