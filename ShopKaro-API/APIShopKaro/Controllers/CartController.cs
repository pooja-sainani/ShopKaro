using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Threading.Tasks;
using APIShopKaro.Models;
using APIShopKaro.Services;

namespace APIShopKaro.Controllers
{
    [RoutePrefix("api/Cart")]
    public class CartController : ApiController
    {
        [HttpPost]
        [Route("AddProductToCart")]
        public HttpResponseMessage AddProductToCart(CART product)
        {
            try
            {
                var cartService = new CartService();
                var id = cartService.AddProductInCart(product);
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
        /// Get all products for a category
        /// </summary>
        /// <param name="buyerId"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetProductsForCart/{buyerId}")]
        public HttpResponseMessage GetProductsForCart(Guid? buyerId)
        {
            try
            {
                var cartService = new CartService();
                var products = cartService.GetAllCartProducts(buyerId);
                var response = Request.CreateResponse(HttpStatusCode.OK, products);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Delete Product
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("DeleteProductFromCart/{id}")]
        public HttpResponseMessage DeleteProductFromCart(Guid? id)
        {
            try
            {
                var cartService = new CartService();
                var product = cartService.DeleteProduct(id);
                var response = Request.CreateResponse(HttpStatusCode.OK, product);
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
