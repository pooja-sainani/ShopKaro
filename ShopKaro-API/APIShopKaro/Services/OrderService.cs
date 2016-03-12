using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIShopKaro.Models;

namespace APIShopKaro.Services
{
    public class OrderService
    {
        public Guid AddNewOrder(ORDER order)
        {
            try
            {
                if (order == null)
                    throw new ArgumentNullException("Product", "Product can not be null");

                // check if all required fields are present
                if ((order.BUYERID == null || order.BUYERID == Guid.Empty))
                    throw new ArgumentException("Some mandatory parameters required to add a new product are missing", "Product");

                if (order.ID == null || order.ID == Guid.Empty)
                    order.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.ORDERS.Add(order);
                        db.SaveChanges();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                }

                return order.ID;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        public bool AddNewProductOrder(ORDERPRODUCT order)
        {
            try
            {


                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.ORDERPRODUCTS.Add(order);

                        db.SaveChanges();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                        return false;
                    }
                }

                return true;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Get all product orders for a user
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        public List<ORDER> GetAllProductOrdersById(Guid? categoryId)
        {
            try
            {
                if (!categoryId.HasValue || categoryId.Value == Guid.Empty)
                    throw new ArgumentNullException("CategoryId", "CategoryId can not be null");

                List<ORDER> products = new List<ORDER>();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        products = (from p in db.ORDERS
                                    where p.BUYERID == categoryId && p.ISACTIVE == true && p.ISSERVICE == false
                                    select p).ToList();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (ArgumentNullException e)
                    {
                    }
                }
                return products;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Get all product of a order for a user
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        public List<OrderProducts> GetAllProductForOrder(Guid? categoryId)
        {
            try
            {
                if (!categoryId.HasValue || categoryId.Value == Guid.Empty)
                    throw new ArgumentNullException("CategoryId", "OrderId can not be null");

                List<OrderProducts> products = new List<OrderProducts>();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        products = (from p in db.ORDERPRODUCTS
                                    join pr in db.PRODUCTS on p.PRODUCTID equals pr.ID
                                    where p.ORDERID == categoryId
                                    select new OrderProducts { QUANTITY = (int)p.QUANTITY, ProductName = pr.NAME, Price = (Decimal)pr.PRICE }).ToList();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (ArgumentNullException e)
                    {
                    }
                }
                return products;
            }
            catch (Exception e)
            {
                throw;
            }
        }



        /// <summary>
        /// Add new product to Cart
        /// </summary>
        /// <param name="product"></param>
        /// <returns></returns>
        public Guid AddServiceInOrder(SERVICEORDER product)
        {
            try
            {

                if (product == null)
                    throw new ArgumentNullException("Service", "Service can not be null");

                // check if all required fields are present
                if ((product.SERVICEID == null || product.SERVICEID == Guid.Empty) ||
                    (product.BUYERID == null || product.BUYERID == Guid.Empty))
                    throw new ArgumentException("Some mandatory parameters required to add a new product are missing", "Product");
                try
                {
                    using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                    {
                        try
                        {
                            db.SERVICEORDERs.Add(product);
                            db.SaveChanges();
                        }
                        catch (System.Data.DataException e)
                        {
                            throw new Exception(e.InnerException.InnerException.Message);
                        }
                    }
                    return product.SERVICEID;
                }
                catch (Exception e)
                {
                    throw new Exception(e.InnerException.InnerException.Message);
                }

            }
            catch (Exception e)
            {
                throw new Exception(e.InnerException.InnerException.Message);
            }



        }

        /// <summary>
        /// Get all Service order for a user
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        public List<SubscribedService> GetAllSubscribedServiceById(Guid? categoryId)
        {
            try
            {
                if (!categoryId.HasValue || categoryId.Value == Guid.Empty)
                    throw new ArgumentNullException("CategoryId", "OrderId can not be null");

                List<SubscribedService> services = new List<SubscribedService>();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        services = (from p in db.SERVICEORDERs
                                    join pr in db.SERVICES on p.SERVICEID equals pr.ID
                                    join seller in db.USERS on pr.SELLERID equals seller.ID
                                    where p.BUYERID == categoryId
                                    select new SubscribedService { ID = pr.ID, Name=pr.NAME,Price = pr.PRICE,Details=pr.DETAILS,SellerName=seller.NAME,SellerContact=seller.CONTACTNUMBER,SellerPlace=seller.PLACE,SellerCity=seller.CITY }).ToList();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (ArgumentNullException e)
                    {
                    }
                }
                return services;
            }
            catch (Exception e)
            {
                throw;
            }
        }

    }
}

