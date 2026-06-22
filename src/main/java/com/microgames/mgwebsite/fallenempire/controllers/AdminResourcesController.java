package com.microgames.mgwebsite.fallenempire.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.microgames.mgwebsite.fallenempire.dto.BuildingForm;
import com.microgames.mgwebsite.fallenempire.dto.CategoryForm;
import com.microgames.mgwebsite.fallenempire.dto.RecipeForm;
import com.microgames.mgwebsite.fallenempire.dto.RecipeIngredientForm;
import com.microgames.mgwebsite.fallenempire.dto.ResourceTypeForm;
import com.microgames.mgwebsite.fallenempire.dto.TierForm;
import com.microgames.mgwebsite.fallenempire.entities.Building;
import com.microgames.mgwebsite.fallenempire.entities.Recipe;
import com.microgames.mgwebsite.fallenempire.entities.RecipeIngredient;
import com.microgames.mgwebsite.fallenempire.entities.ResourceCategory;
import com.microgames.mgwebsite.fallenempire.entities.ResourceTier;
import com.microgames.mgwebsite.fallenempire.entities.ResourceType;
import com.microgames.mgwebsite.fallenempire.repository.BuildingRepository;
import com.microgames.mgwebsite.fallenempire.repository.RecipeIngredientRepository;
import com.microgames.mgwebsite.fallenempire.repository.RecipeRepository;
import com.microgames.mgwebsite.fallenempire.repository.ResourceCategoryRepository;
import com.microgames.mgwebsite.fallenempire.repository.ResourceTierRepository;
import com.microgames.mgwebsite.fallenempire.repository.ResourceTypeRepository;

// Panel temporal para cargar a mano el árbol de recursos del juego.
// No tiene nada de lógica de juego: solo formularios simples que guardan en la base de datos.
// Pendiente: borrar este controlador (y sus plantillas) cuando ya no se necesite cargar datos a mano.
@Controller
public class AdminResourcesController {

    private final ResourceTierRepository tierRepository;
    private final ResourceCategoryRepository categoryRepository;
    private final BuildingRepository buildingRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public AdminResourcesController(
            ResourceTierRepository tierRepository,
            ResourceCategoryRepository categoryRepository,
            BuildingRepository buildingRepository,
            ResourceTypeRepository resourceTypeRepository,
            RecipeRepository recipeRepository,
            RecipeIngredientRepository recipeIngredientRepository) {

        this.tierRepository = tierRepository;
        this.categoryRepository = categoryRepository;
        this.buildingRepository = buildingRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    // Carga todo lo necesario para pintar la página: los formularios vacíos
    // y las listas de "ya existentes" de cada tabla.
    @GetMapping("/fallenempire/admin")
    public String panel(Model model) {

        model.addAttribute("tierForm", new TierForm());
        model.addAttribute("categoryForm", new CategoryForm());
        model.addAttribute("buildingForm", new BuildingForm());
        model.addAttribute("resourceTypeForm", new ResourceTypeForm());
        model.addAttribute("recipeForm", new RecipeForm());
        model.addAttribute("recipeIngredientForm", new RecipeIngredientForm());

        cargarListasComunes(model);

        return "fallenempire/admin";
    }

    // Junta en el Model todo lo que las distintas secciones de la página
    // necesitan para mostrar sus listas y rellenar los <select>.
    private void cargarListasComunes(Model model) {

        model.addAttribute("tiers", tierRepository.findAllByOrderByOrderIndexAsc());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("buildings", buildingRepository.findAll());
        model.addAttribute("resources", resourceTypeRepository.findAllByOrderByNameAsc());
        model.addAttribute("recipes", recipeRepository.findAll());
        model.addAttribute("recipeIngredients", recipeIngredientRepository.findAll());
    }

    // ==================== TIER ====================

    @PostMapping("/fallenempire/admin/tier")
    public String crearTier(@ModelAttribute TierForm form) {

        ResourceTier tier = new ResourceTier();

        tier.setCode(form.getCode().trim().toUpperCase());
        tier.setName(form.getName().trim());
        tier.setOrderIndex(form.getOrderIndex());

        tierRepository.save(tier);

        return "redirect:/fallenempire/admin";
    }

    // ==================== CATEGORÍA ====================

    @PostMapping("/fallenempire/admin/category")
    public String crearCategoria(@ModelAttribute CategoryForm form) {

        ResourceCategory category = new ResourceCategory();

        category.setCode(form.getCode().trim().toUpperCase());
        category.setName(form.getName().trim());

        categoryRepository.save(category);

        return "redirect:/fallenempire/admin";
    }

    // ==================== EDIFICIO ====================

    @PostMapping("/fallenempire/admin/building")
    public String crearEdificio(@ModelAttribute BuildingForm form) {

        Building building = new Building();

        building.setCode(form.getCode().trim().toUpperCase());
        building.setName(form.getName().trim());

        buildingRepository.save(building);

        return "redirect:/fallenempire/admin";
    }

    // ==================== RECURSO ====================

    @PostMapping("/fallenempire/admin/resource")
    public String crearRecurso(@ModelAttribute ResourceTypeForm form) {

        ResourceTier tier = tierRepository
                .findById(form.getTierId())
                .orElseThrow(() -> new IllegalArgumentException("Tier no encontrado"));

        ResourceCategory category = categoryRepository
                .findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        ResourceType resource = new ResourceType();

        resource.setCode(form.getCode().trim().toUpperCase());
        resource.setName(form.getName().trim());
        resource.setTier(tier);
        resource.setCategory(category);
        resource.setAbundance(form.getAbundance());
        resource.setDescription(form.getDescription());

        resourceTypeRepository.save(resource);

        return "redirect:/fallenempire/admin";
    }

    // ==================== RECETA ====================

    @PostMapping("/fallenempire/admin/recipe")
    public String crearReceta(@ModelAttribute RecipeForm form) {

        Building building = buildingRepository
                .findById(form.getBuildingId())
                .orElseThrow(() -> new IllegalArgumentException("Edificio no encontrado"));

        ResourceType outputResource = resourceTypeRepository
                .findById(form.getOutputResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Recurso no encontrado"));

        Recipe recipe = new Recipe();

        recipe.setBuilding(building);
        recipe.setOutputResource(outputResource);

        recipeRepository.save(recipe);

        return "redirect:/fallenempire/admin";
    }

    // ==================== INGREDIENTE DE RECETA ====================

    @PostMapping("/fallenempire/admin/recipe-ingredient")
    public String anadirIngrediente(@ModelAttribute RecipeIngredientForm form) {

        Recipe recipe = recipeRepository
                .findById(form.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Receta no encontrada"));

        ResourceType resource = resourceTypeRepository
                .findById(form.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Recurso no encontrado"));

        RecipeIngredient ingredient = new RecipeIngredient();

        ingredient.setRecipe(recipe);
        ingredient.setResource(resource);
        ingredient.setQuantity(form.getQuantity());

        recipeIngredientRepository.save(ingredient);

        return "redirect:/fallenempire/admin";
    }
}