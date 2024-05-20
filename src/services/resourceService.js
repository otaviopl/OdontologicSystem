const RESOURCE_KEY = 'patients';

const getResources = () => {
  const resources = localStorage.getItem(RESOURCE_KEY);
  return resources ? JSON.parse(resources) : [];
};

const addResource = (resource) => {
  const resources = getResources();
  resources.push(resource);
  localStorage.setItem(RESOURCE_KEY, JSON.stringify(resources));
};

const updateResource = (id, updatedResource) => {
  const resources = getResources();
  const index = resources.findIndex(resource => resource.id === id);
  if (index !== -1) {
    resources[index] = updatedResource;
    localStorage.setItem(RESOURCE_KEY, JSON.stringify(resources));
  }
};

const deleteResource = (id) => {
  let resources = getResources();
  resources = resources.filter(resource => resource.id !== id);
  localStorage.setItem(RESOURCE_KEY, JSON.stringify(resources));
};

export { getResources, addResource, updateResource, deleteResource };
