package customer.java_consumer.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.cds.Result;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.booksservice.BooksService_;
import cds.gen.booksservice.MyBooks_;
import cds.gen.catalogservice.CatalogService_;

@Component
@ServiceName(BooksService_.CDS_NAME)
public class BooksServiceHandler implements EventHandler{
    @Autowired
    @Qualifier(CatalogService_.CDS_NAME)
    CqnService catalog;

    @On(event = CqnService.EVENT_READ, entity = MyBooks_.CDS_NAME)
    public Result readBooks(CdsReadEventContext context) {
        return catalog.run(context.getCqn());
    }
}
