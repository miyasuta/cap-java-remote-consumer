using { CatalogService as external } from './external/CatalogService';

service BooksService {
    @readonly
    entity MyBooks as projection on external.Books;
}