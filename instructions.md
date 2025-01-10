# Case - Order management

In this case, you are tasked with creating a backend with a REST API for simple order management.  
Your focus should be on creating fully working services and managing the data.

Good luck!

## Description

The interface should support the following functions:

- Create customer/order
- Fetch customer/order
- Update order

## Information

The following information must exist on the matching entity:

| Customer                              | Order          | Order row                    |
|---------------------------------------|----------------|------------------------------|
| Name                                  | Customer       | Article                      |
| (Pen, notepad, paper or erasers)      |                |                              |
| Type (small/large business, personal) | Order row      | Number                       |
|                                       | Total sum      | Price per article            |
|                                       | Total discount | Total sum for order row      |
|                                       |                | Total discount for order row |

## Additional functionality

The following functions should also be implemented:

- Business customers shall have a 10% discount on all articles
- Large business customers have an additional 20% discount on pens and paper
- If you order over 10 000 SEK, you get a bicycle for free