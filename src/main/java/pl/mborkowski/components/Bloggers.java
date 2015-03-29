package pl.mborkowski.components;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WrappedSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.vaadin.dialogs.ConfirmDialog;
import pl.mborkowski.Data;
import pl.mborkowski.bean.Blogger;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.ui.MainUI;

public class Bloggers extends CustomComponent{

    private VerticalLayout layout;
    private HorizontalLayout menu;
    private Table bloggers;
    private BeanItemContainer beanItemContainer;
    private Button add;

    public Bloggers(){
        layout  = new VerticalLayout();
        setWidth(Constant.UI.DEFAULT_UI_SIZE);
        layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);

        prepareMenu();
        layout.addComponent(menu);

        prepareUsersTable();
        layout.addComponent(bloggers);

        setCompositionRoot(layout);
    }

    private void setButtonsStyle(){
        add.setStyleName("margin");
    }

    private void prepareMenu(){
        menu = new HorizontalLayout();
        menu.setMargin(new MarginInfo(false, true, false, false));
        add = new Button(FontAwesome.PLUS);

        add.addClickListener(clickEvent -> {
            MainUI root = (MainUI)this.getParent().getParent().getParent();

            root.setPageContent(new pl.mborkowski.components.Blogger());
        });
        menu.addComponents(add);
        setButtonsStyle();
    }

    private void prepareUsersTable(){
        bloggers = new Table();
        beanItemContainer = new BeanItemContainer(Blogger.class);
        bloggers.setContainerDataSource(beanItemContainer);
        beanItemContainer.addAll(Data.users);

        bloggers.setColumnHeader("name", Constant.Label.NAME);
        bloggers.setColumnHeader("login", Constant.Label.LOGIN);
        bloggers.setColumnHeader("registered", Constant.Label.REGISTER_DATE);

        bloggers.addGeneratedColumn(Constant.Label.EDIT, new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object itemId, Object columnId) {
                Button btn = new Button(FontAwesome.EDIT);
                btn.addClickListener(clickEvent -> {
                    MainUI root = (MainUI)getParent().getParent().getParent();
                    root.setPageContent(new pl.mborkowski.components.Blogger((Blogger) itemId));
                });
                return btn;
            }
        });

        bloggers.addGeneratedColumn(Constant.Label.DELETE, new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object itemId, Object columnId) {
                Button btn = new Button(FontAwesome.MINUS);
                MainUI root = (MainUI)getParent().getParent().getParent();
                btn.addClickListener(event -> {
                    ConfirmDialog.show(root, Constant.Label.CONFIRM,
                        new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    Data.users.remove(itemId);
                                    source.removeItem(itemId);
                                }
                            }
                    });

                });
                return btn;
            }
        });

        bloggers.setVisibleColumns("name", "login", "registered", Constant.Label.EDIT, Constant.Label.DELETE);
        bloggers.setSizeFull();
        bloggers.setImmediate(true);

    }

    public static boolean isLoggedIn(){
        WrappedSession currentSession = VaadinService.getCurrentRequest().getWrappedSession();
        return currentSession.getAttribute("user") != null;
    }


}
