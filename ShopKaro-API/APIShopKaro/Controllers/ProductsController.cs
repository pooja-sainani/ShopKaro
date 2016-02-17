using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using APIShopKaro.Models;
using APIShopKaro.Services;

namespace APIShopKaro.Controllers
{
    [RoutePrefix("api/Products")]
    public class ProductsController : ApiController
    {
        /// <summary>
        /// Add new product
        /// </summary>
        /// <param name="product"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("AddNewProduct")]
        public HttpResponseMessage AddNewProduct(PRODUCT product)
        {
            try
            {
                var productsService = new ProductsService();
                var id = productsService.AddNewProduct(product);
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
        /// <param name="categoryId"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("GetAllProductsByCategory")]
        public HttpResponseMessage GetAllProductsByCategory(Guid? categoryId)
        {
            try
            {
                var productsService = new ProductsService();
                var products = productsService.GetAllProductsByCategory(categoryId);
                var response = Request.CreateResponse(HttpStatusCode.OK, products);
                return response;
            }
            catch(Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Get product by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("GetProductById")]
        public HttpResponseMessage GetProductById(Guid? id)
        {
            try
            {
                var productsService = new ProductsService();
                var product = productsService.GetProductById(id);
                var response = Request.CreateResponse(HttpStatusCode.OK, product);
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
        [HttpPost]
        [Route("DeleteProduct")]
        public HttpResponseMessage DeleteProduct(Guid? id)
        {
            try
            {
                var productsService = new ProductsService();
                var product = productsService.DeleteProduct(id);
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
