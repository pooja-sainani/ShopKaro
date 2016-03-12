using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using APIShopKaro.Models;
using APIShopKaro.Services;
using System.Web.Http.Description;
namespace APIShopKaro.Controllers
{
    [RoutePrefix("api/Orders")]
    public class OrderController : ApiController
    {

        [HttpPost]
        [Route("AddOrder")]
        public HttpResponseMessage AddOrder(List<ProductsInOrder> orp)
        {
            //ProductsInOrder pr1 = new ProductsInOrder();
            //pr1.BUYERID = new Guid("E6AB9AB0-0F18-405A-A4A2-610E52C18B7D");
            //pr1.QUANTITY = 2;
            //pr1.Price = 20;
            //pr1.PRODUCTID = new Guid("9869EA11-DC7F-4114-81E9-9779481DDC3A");
            //orp.Add(pr1);
            //ProductsInOrder pr2 = new ProductsInOrder();
            //pr2.BUYERID = new Guid("E6AB9AB0-0F18-405A-A4A2-610E52C18B7D");
            //pr2.QUANTITY = 2;
            //pr2.Price = 20;
            //pr2.PRODUCTID = new Guid("DE04F38F-D0D1-44FB-9B15-1738FF49C411");
            //orp.Add(pr2);
            //ProductsInOrder pr3 = new ProductsInOrder();
            //pr3.BUYERID = new Guid("E6AB9AB0-0F18-405A-A4A2-610E52C18B7D");
            //pr3.QUANTITY = 2;
            //pr3.Price = 20;
            //pr3.PRODUCTID = new Guid("51FD75B2-F819-4DE6-82F4-7C91C38D874D");
            //orp.Add(pr3);
            //ProductsInOrder pr4 = new ProductsInOrder();
            //pr4.BUYERID = new Guid("E6AB9AB0-0F18-405A-A4A2-610E52C18B7D");
            //pr4.QUANTITY = 2;
            //pr4.Price = 20;
            //pr4.PRODUCTID = new Guid("D12E7EC5-63E3-419D-B5A1-4EDFD34B5189");
            //orp.Add(pr4);
            Decimal total_price = 0;
            try
            {
                foreach (ProductsInOrder pr in orp)
                {
                    total_price = total_price + pr.Price;

                }

                var userService = new UserService();
                var user = userService.GetUserById(orp[0].BUYERID);
                if (user == null ||user.ID == null || user.ID == Guid.Empty)
                {
                    var error = Request.CreateResponse(HttpStatusCode.NoContent, "Buyer not valid");
                    return error;
                }
                ORDER or = new ORDER();
                or.BUYERID = (System.Guid)user.ID;
                or.ISACTIVE = true;
                or.PLACE = user.PLACE;
                or.CITY = user.CITY;
                or.STATE = user.STATE;
                or.TOTATPRICE = total_price;
                or.ISSERVICE = false;
                or.ORDERDATE = DateTime.Now;
                var orderid = new OrderService().AddNewOrder(or);
                if (orderid == null || orderid == Guid.Empty)
                {
                    var error = Request.CreateResponse(HttpStatusCode.NoContent, "Could not place order");
                    return error;
                }
                foreach (ProductsInOrder pr in orp)
                {
                    ORDERPRODUCT op = new ORDERPRODUCT();
                    op.ORDERID = orderid;
                    op.PRICE = pr.Price;
                    op.QUANTITY = pr.QUANTITY;
                    op.PRODUCTID = pr.PRODUCTID;
                    bool ret = new OrderService().AddNewProductOrder(op);
                    if (!ret)
                    {
                        var error = Request.CreateResponse(HttpStatusCode.NoContent, "Some Products not added");
                        return error;
                    }
                }
                List<CartProduct> cart = new CartService().GetAllCartProducts(or.BUYERID);
                foreach(CartProduct cp in cart)
                {
                    Boolean res = new CartService().DeleteProduct(cp.ID);
                    
                }
                var response = Request.CreateResponse(HttpStatusCode.OK, orderid);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }


        [HttpPost]
        [Route("SubscribeService")]
        public HttpResponseMessage SubscribeService(SERVICEORDER orp)
        {
            try
            {
                var orderservice = new OrderService();
                orp.Orderdate = DateTime.Now;
                var id = orderservice.AddServiceInOrder(orp);
                var response = Request.CreateResponse(HttpStatusCode.OK, id);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }

        }

        /// <summary>
        /// Get all orders of Products for a User
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetAllProductOrdersByUser/{userId}")]
        public HttpResponseMessage GetAllProductOrdersByUser(Guid? userId)
        {
            try
            {
                var orderService = new OrderService();
                var orders = orderService.GetAllProductOrdersById(userId);
                var response = Request.CreateResponse(HttpStatusCode.OK, orders);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Get all Products of order for a User
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetAllProductsByCategoryOrder/{orderId}")]
        public HttpResponseMessage GetAllProductsByCategoryOrder(Guid? orderId)
        {
            try
            {
                var orderService = new OrderService();
                var orders = orderService.GetAllProductForOrder(orderId);
                var response = Request.CreateResponse(HttpStatusCode.OK, orders);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Get all orders of Products for a User
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetAllSubscribedServiceByUser/{userId}")]
        public HttpResponseMessage GetAllSubscribedServiceByUser(Guid? userId)
        {
            try
            {
                var orderService = new OrderService();
                var orders = orderService.GetAllSubscribedServiceById(userId);
                var response = Request.CreateResponse(HttpStatusCode.OK, orders);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

    }
}
