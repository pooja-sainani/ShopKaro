using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIShopKaro.Models;

namespace APIShopKaro.Services
{
    public class ServicessService
    {

        /// <summary>
        /// Add new service
        /// </summary>
        /// <param name="service"></param>
        /// <returns></returns>
        public Guid AddNewService(SERVICE service)
        {
            try
            {
                if (service == null)
                    throw new ArgumentNullException("Service", "Service can not be null");

                // check if all required fields are present
                if ((!service.CATEGORYID.HasValue || service.CATEGORYID.Value == Guid.Empty) || service.NAME == null ||
                    !service.PRICE.HasValue || (!service.SELLERID.HasValue || service.SELLERID.Value == Guid.Empty))
                    throw new ArgumentException("Some mandatory parameters required to add a new service are missing", "Service");

                if (!service.ID.HasValue || service.ID.Value == Guid.Empty)
                    service.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.SERVICES.Add(service);
                        db.SaveChanges();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (Exception e)
                    {
                        throw;
                    }
                }

                return service.ID.Value;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Get all services for a category
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        public List<SERVICE> GetAllServicesByCategory(Guid? categoryId)
        {
            try
            {
                if (!categoryId.HasValue || categoryId.Value == Guid.Empty)
                    throw new ArgumentNullException("CategoryId", "CategoryId can not be null");

                List<SERVICE> services = new List<SERVICE>();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        var pr = from p in db.SERVICES
                                 where p.CATEGORYID == categoryId
                                 select p;
                        if (services.Count() > 0)
                            services = pr.ToList();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (Exception e)
                    {
                        throw;
                    }
                }

                return services;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Get service by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public SERVICE GetServiceById(Guid? id)
        {
            try
            {
                if (!id.HasValue || id.Value == Guid.Empty)
                    throw new ArgumentNullException("ID", "ID can not be null");

                SERVICE service;

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        var pr = from p in db.SERVICES
                                 where p.CATEGORYID == id
                                 select p;
                        if (pr.Count() != 1)
                            return null;
                        service = pr.Single();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (Exception e)
                    {
                        throw;
                    }
                }
                return service;
            }
            catch (Exception e)
            {
                throw;
            }
        }
    }
}