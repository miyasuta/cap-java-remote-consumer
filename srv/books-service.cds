using { CatalogService as external } from './external/CatalogService';
using { test as db } from '../db/schema';

service BooksService {
    @readonly
    entity Books as projection on external.Books;
    entity Customers as projection on db.Customers;
}